// Pase de compilados de WS (.AAR) al FTP para PRE y PRO

import groovy.transform.Field
import java.text.SimpleDateFormat

def entorno = ""
def proyecto = ""
def origen_git = ""
def aars = ""
def nom_destino = ""
def lista_aar = [:]
def userID = "" // Se incluye el Usuario que lanza el proceso para validaciones Sonar y logs
def jobBaseName = ""
def fecha =null
def VERSION = ""
def ENT_FTP=""
def ENT_GIT=""


def proyectos = [
ASODEF: 'git@git.servdev.mdef.es:personal/ASODEF/wsasodefentirex.git',
PORTAL_PERSONAL: 'git@git.servdev.mdef.es:personal/PORTAL_PERSONAL/WSPortalEntireX.git',
SAPROMIL: 'git@git.servdev.mdef.es:personal/SAPROMIL/wssiperdef.git', 
SEMILCAR: 'git@git.servdev.mdef.es:formacion/SEMILCAR/WSSemilcar.git',
SIPEC: 'git@git.servdev.mdef.es:personal/sipec_migracion/wssipec.git',
SIPEROPS: 'git@git.servdev.mdef.es:personal/SIPEROPS/SIPEROPS_WS.git',
SIPERDEF: 'git@git.servdev.mdef.es:personal/siperdef/WSSiperdef.git',
SIPERDEFSEDE: 'git@git.servdev.mdef.es:lmunma1/ws-siperdef-sede-luis.git'
]


def inputBox = ""
def inputBox2 = ""

// Ejecutar esto siempre lo primero 
if (currentBuild.getBuildCauses().toString().contains('BranchIndexingCause')) {
  println "INFO: Proceso abortado. Ha sido lanzado para Indexación de ramas"
  currentBuild.result = 'ABORTED' 
  return
}

pipeline {

    environment{
	    env_desa = 'Desarrollo'
	    env_pre  = 'Pre-produccion'
	    env_pro  = 'Produccion'
	    env_val  = 'Validacion'
	    env_hotf = 'Hotfix'
	}

	agent {node { label 'linux' }}
	
    stages{
        stage('Seleccionar Proyecto:') {
			steps {
				script{
				    if(!env.BRANCH_NAME.matches("master"))
                        println "******RAMA:  ${env.BRANCH_NAME}"
                        
				    try{
                        jobBaseName = "${env.JOB_NAME}".split('/')[0]
                        echo "Este proceso guarda log de las acciones realizadas en la ruta: /var/log/jenkins/${jobBaseName}.log"
                        fecha = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                        userID = currentBuild.getRawBuild().getCauses()[0].getUserId()
                        if (userID==""){
                            currentBuild.result = 'ABORTED'
                            println "Usuario no identificado"
                            throw new hudson.AbortException('')
                        }
                    }catch(err){
                        echo "Fin Recuperar usuario" + err
                        currentBuild.result = 'ABORTED'
                        throw new hudson.AbortException('')
                    }
                    
					try {
					    timeout(time: 60, unit: 'SECONDS') {
					                // nom_proyectos debe coincidir con la variable "proyectos"
        						    def nom_proyectos = "ASODEF\nPORTAL_PERSONAL\nSAPROMIL\nSEMILCAR\nSIPEC\nSIPEROPS\nSIPERDEF\nSIPERDEFSEDE"
        						    
        							// Se muestra un formulario para elegir en que proyecto se quiere desplegar
        							proyecto = input(message: 'Interacción Usuario requerido', id: 'userInput', 
        						    ok: 'Seleccionar',
        						    parameters: [[
        						    $class: 'ChoiceParameterDefinition', 
        						    choices: "${nom_proyectos}", 
        						    description: 'Selección de Proyecto para paso de WS a FTP',
        						    name:"proyectos"
        						    ]])
        						
        						    //obtener el nombre de la carpeta donde se va a guardar el contenido al hacer clone a partir de la ruta GIT 
        						    origen_git = proyectos[proyecto].substring(proyectos[proyecto].lastIndexOf("/") + 1)
        						    origen_git = origen_git.replace(".git","")
        						    echo("Carpeta del proyecto en GIT:" + origen_git)
									sshagent(['jenkins-generated-ssh-key']) {
                                         // Antes de hacer clone, borra el contenido si existe
                                         sh "rm -rf ${origen_git}" 
                                         sh "git clone --branch develop ${proyectos[proyecto]}"
                                    }

        				}
        			} catch(err) {		// Si Cancelamos el formulario.	
						    println ("Proceso cancelado por error:" + err)
                            currentBuild.result = 'ABORTED'
                            throw new hudson.AbortException('')
                    }
                } 
			}
        }

		stage('Seleccionar entorno:') {
			steps {
				script{
					try {
					    timeout(time: 60, unit: 'SECONDS') {
					                // Entornos a los que se pueden pasar los archivos: PRE y PRO
        						    def entornos = "${env_pre}\n${env_pro}"
        							// Se muestra un formulario para elegir en que entorno se quiere desplegar
        							entorno = input(message: 'Interacción Usuario requerido',
        						    ok: 'Seleccionar',
        						    parameters: [choice(name: 'Elección Entorno', choices: "${entornos}", description: 'Selección de entorno para pase a FTP')])

                            if (entorno==env_pre){
							 if ((proyecto=="SEMILCAR") || (proyecto == "SIPERDEFSEDE")){
										ENT_GIT = "source/PRE"
								}else{
									ENT_GIT = "WebServicesPreproduccion"
								}
								ENT_FTP = "PRE"
                		    }
                		    if (entorno==env_pro){
								if (proyecto=="SEMILCAR"){
									ENT_GIT = "source/PRODU"
								}else if (proyecto=="SIPERDEFSEDE"){
								    ENT_GIT = "source/PRO"
								}else{
									ENT_GIT = "WebServicesProduccion"
								}
                		        
									ENT_FTP = "PRO"
                		    }

        				}
        			} catch(err) {		// Si Cancelamos el formulario.	
        				user = err.getCauses()[0].getUser().toString()
                        currentBuild.result = 'ABORTED'
                        throw new hudson.AbortException('')
                    }
                } 
			}
        }
        
        stage('Seleccionar WS/WAR a subir:') {
			steps {
				script{
					try {
					    timeout(time: 180, unit: 'SECONDS') {
							// Recupera el repositorio
                        sshagent(['jenkins-generated-ssh-key']) {
                            // Antes de hacer clone, borra el contenido si existe
                           // sh "rm -rf ${origen_git}" 

                           // sh "git clone --branch develop ${proyectos[proyecto]}"
							sleep(5)
							def WS = pwd()
							sh "cd ${WS}/${origen_git}/${ENT_GIT}"
							//sh "touch opciones; chmod 777 opciones"
							def opAar = sh '''                            
                                        ls ${WS}/${origen_git}/${ENT_GIT}/*.aar
                                    '''
                            def opWar = sh '''                            
                                        ls ${WS}/${origen_git}/${ENT_GIT}/*.war
                                    '''
							
							 
                            echo "opciones --> ${opAar} --> ${opWar}"
                        }

		                    aars = input(
                             id: 'userInput', message: 'Introducir WS/WAR para pasar al FTP:', 
                             parameters: [
                             [$class: 'TextParameterDefinition', defaultValue: '', description: 'Introducir nombres de AAR/WAR separados por coma (WS1.aar,WS2.aar...)\nImportante!!: Se diferencia entre mayúsculas y minúsculas,\n escribirlo como esta en GIT', name: 'inputBox']
                            ])
                            //aars = userInput['inputBox']
                            echo ("Tiene coma:" + aars.indexOf(','));
                            echo ("Tiene enter:" + aars.indexOf('\n'));
                            echo ("WS/WAR introducidos al desplegar: "+ aars)
                            lista_aar = aars.split(',')
                            echo ("WS introducidos: "+ lista_aar.size())
        				}
        			} catch(err) {		// Si Cancelamos el formulario.	
        					user = err.getCauses()[0].getUser().toString()
                            currentBuild.result = 'ABORTED'
                            throw new hudson.AbortException('')
                    }
                } 
			}
        }
        

/*
        stage('Seleccionar entorno:') {
			steps {
				script{
					try {
					    timeout(time: 60, unit: 'SECONDS') {
					                // Entornos a los que se pueden pasar los archivos: PRE y PRO
        						    def entornos = "${env_pre}\n${env_pro}"
        							// Se muestra un formulario para elegir en que entorno se quiere desplegar
        							entorno = input(message: 'Interacción Usuario requerido',
        						    ok: 'Seleccionar',
        						    parameters: [choice(name: 'Elección Entorno', choices: "${entornos}", description: 'Selección de entorno para pase a FTP')])
									VERSION = new Date().format( 'yyyyMMdd' )
									if (entorno==env_pre){
								if ((proyecto=="SEMILCAR") || (proyecto == "SIPERDEFSEDE")){
										ENT_GIT = "source/PRE"
								}else{
									ENT_GIT = "WebServicesPreproduccion"
								}
								ENT_FTP = "PRE"
                		    }
                		    if (entorno==env_pro){
								if (proyecto=="SEMILCAR"){
									ENT_GIT = "source/PRODU"
								}else if (proyecto=="SIPERDEFSEDE"){
								    ENT_GIT = "source/PRO"
								}else{
									ENT_GIT = "WebServicesProduccion"
								}
                		        
									ENT_FTP = "PRO"
                		    }
        				}
        			} catch(err) {		// Si Cancelamos el formulario.	
        				user = err.getCauses()[0].getUser().toString()
                        currentBuild.result = 'ABORTED'
                        throw new hudson.AbortException('')
                    }
                } 
			}
        }
*/
        stage('Paso de archivos al FTP:') {
            steps {
                script{
                    try {                                                   
                        // FTP al entorno seleccionado
                        
                        echo "Paso de archivos al FTP!: ${entorno} - ${proyecto} : ${proyectos[proyecto]} " 

                        if (proyecto=='' || lista_aar.size()==0 || entorno=='' ){
                		    currentBuild.result = 'FAILURE'
                            throw new hudson.AbortException('Faltan datos para poder completar la tarea')
            			}
            			
                        // Recupera el repositorio
						/*
                        sshagent(['jenkins-generated-ssh-key']) {
                            // Antes de hacer clone, borra el contenido si existe
                            sh "rm -rf ${origen_git}" 

                            sh "git clone --branch develop ${proyectos[proyecto]}"
                        }
						*/
                        
                        // Prepara el pase al FTP
                    	def FTP_PASES = [:]
                		FTP_PASES.name =			"FTP Pases"
		                FTP_PASES.host =			"ftpaplicaciones.mdef.es"
                		FTP_PASES.user =			"${env.FTP_USER}"
		                FTP_PASES.password =		"${env.FTP_PWD}"
		                FTP_PASES.allowAnyHosts =	true
		                FTP_PASES_RUTA =     "/SFTP/DIVINDES"
		
                		script {
							echo "subida al SFTP ********************"
							
                		 //   def VERSION = new Date().format( 'yyyyMMdd' )
                		 //   def ENT_FTP=""
                		 //   def ENT_GIT=""
            			 /*   if (entorno==env_pre){
								if ((proyecto=="SEMILCAR") || (proyecto == "SIPERDEFSEDE")){
										ENT_GIT = "source/PRE"
								}else{
									ENT_GIT = "WebServicesPreproduccion"
								}
								ENT_FTP = "PRE"
                		    }
                		    if (entorno==env_pro){
								if (proyecto=="SEMILCAR"){
									ENT_GIT = "source/PRODU"
								}else if (proyecto=="SIPERDEFSEDE"){
								    ENT_GIT = "source/PRO"
								}else{
									ENT_GIT = "WebServicesProduccion"
								}
                		        
									ENT_FTP = "PRO"
                		    }
                		    */
                			sh "mkdir -p '${proyecto}'/SERVICIOS/'${VERSION}'/'${ENT_FTP}'"
        			
			                sh "rm -rf ${proyecto}/SERVICIOS/${VERSION}/${ENT_FTP}/doc"
			                sh "rm -rf ${proyecto}/SERVICIOS/${VERSION}/${ENT_FTP}/bin"
			                
			                sh "mkdir -p '${proyecto}'/SERVICIOS/'${VERSION}'/'${ENT_FTP}'/bin"
			                sh "mkdir -p '${proyecto}'/SERVICIOS/'${VERSION}'/'${ENT_FTP}'/doc"
			                 
			                // Copia de archivos que se introdujeron en el segundo paso     
			                try{
    			                for(int i=0; i<lista_aar.size(); i++) {
    			                    //nom_destino = lista_aar[i].substring(lista_aar[i].lastIndexOf("/") + 1)
    			                    sh "cp -r ${origen_git}/${ENT_GIT}/${lista_aar[i]}  ${proyecto}/SERVICIOS/${VERSION}/${ENT_FTP}/bin/"
    			                    echo ("Copia aar:" + lista_aar[i])
                                }
                            }catch(err){
                    			//error "Error al copiar archivos" + err
                                currentBuild.result = 'FAILURE'
                                throw new hudson.AbortException(err)
                    		}
                    		
			                // Copia del documento de despliegue si existe, sino muestra traza y continúa.
                    		try{
                    			sh "cp -r ${origen_git}/${ENT_GIT}/*.doc* ${proyecto}/SERVICIOS/${VERSION}/${ENT_FTP}/doc/"
                    		}catch(err){
                    			//error "Error al copiar documento de despliegue" + err
                    			echo "No encontrado documento de despliegue" + err
                    		}

                            // Elimina del FTP la carpeta para la versión del día y el entorno y hace el pase que ha preparado en local
                            sshCommand(remote: FTP_PASES, command: "rm -rf ${FTP_PASES_RUTA}/${proyecto}/SERVICIOS/${VERSION}/${ENT_FTP}")

                            sshPut(remote: FTP_PASES, from: "${proyecto}", into: "${FTP_PASES_RUTA}")
			
			                echo 'Lista contenido del FTP: '
			                sshCommand(remote: FTP_PASES, command: "ls -alh ${FTP_PASES_RUTA}/${proyecto}/SERVICIOS/${VERSION}/* -R")
							 
		                }
		                
		                def aars_txt = aars.replace("\n",",")
		                def linea_log ="[${fecha.format(new Date())}], ${userID}, ${proyecto}, ${aars}, ${BUILD_URL}"
	    			    // Formato línea: Fecha, usuario, aplicacion, nombres_ws, url ejecución
		       		    echo "antes de grabar log"
			    		sh "echo ${linea_log} >> /var/log/jenkins/${jobBaseName}.log"
                    } catch(err) {
				    	echo "Ha ocurrido un error: " + err.toString()
                        currentBuild.result = 'FAILURE'
                        throw new hudson.AbortException('')
	
			        }
                }
            }
        }
        
        stage('Clean Workspace') {
            steps {
                script{
                	echo "Eliminar archivos del workspace desactivo"
                   // cleanWs()
                }
            }
        }
    }
}	
