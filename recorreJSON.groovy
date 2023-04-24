pipeline {
    agent any
     environment {
        NAME_JOB = 'true'
        USR_JOB    = 'sqlite'
    }

    stages {
        stage('Sample') {
          steps {
            script {
                
def jsonStringDos = ''' {
    "apps": {
        "AGT": 
        [
            {"rama":"develop", "entorno":"Desarrollo,Validacion", "usuario":"lmunma1", "variable":4},
            {"rama":"develop", "entorno":"Desarrollo,Validacion", "usuario":"lmunma1", "variable":4},
            {"rama":"develop", "entorno":"Desarrollo,Validacion", "usuario":"lmunma1", "variable":4}
        ],
        "SIACUDEF": [
            {"rama":"develop", "entorno":"Desarrollo,Validacion", "usuario":"lmunma1", "variable":4},
            {"rama":"develop", "entorno":"Desarrollo,Validacion", "usuario":"lmunma1", "variable":4},
            {"rama":"develop", "entorno":"Desarrollo,Validacion", "usuario":"lmunma1", "variable":4}
             ]
            }
}'''
                userID = currentBuild.getRawBuild().getCauses()[0].getUserId()
                echo "${env.JOB_NAME}"
                echo "${env.BRANCH_NAME}"
                 echo "${userID}"
                
                def propsDos = readJSON text: jsonStringDos
                def app1 = propsDos.apps['SIACUDEF'].rama
                def app2 = propsDos.apps
                def longitud = app2.size()
                echo "$app1"
                echo "$app2"
                echo "$longitud"
                def listaApp = propsDos.apps.SIACUDEF
                
                 if (listaApp!=null){
                      println listaApp
                     println listaApp.size()
                     longitud = listaApp.size()
                    for(int i = 0;i<longitud;i++) {
                      println(listaApp[i].rama)
                      println(listaApp[i].entorno)
                      println(listaApp[i].usuario)
                    }
                 }else{
                     echo "No hay limites"
                 }
              //  for(int i = 0;i<longitud;i++) {
              //        println(propsDos.apps[i].value);
              //  }
               
            }
          }
    }
    }
}
********************************
pipeline {
    agent any

    stages {
        stage('Sample') {
          steps {
            script {
                def jsonString = '''{"analyzer": "static_analyzer", 
                                   "status": "success", 
                                   "hash": "3c8f0dae82136f0a1447de5531e5bd03", 
                                   "scan_type": "zip", 
                                   "file_name": "jenkins-iOS-BuildVerify-GH-PR-6487-38.zip"}'''
                                   
def jsonStringDos = ''' {
    "cars": {
        "Nissan": 
        [
            {"model":"Sentra", "doors":4},
            {"model":"Maxima", "doors":4},
            {"model":"Skyline", "doors":2}
        ],
        "Ford": [
            {"model":"Taurus", "doors":4},
            {"model":"Escort", "doors":4}
             ]
            }
}'''

def jsonStringTres = ''' {
    "apps": {
        "AGT": 
          [
            {"rama":"develop"},
            {"entorno":"Desarrollo,Validacion"},
            {"usuario":"lmunma1,pcasla1"},
			{"variable":"0"}
		],
         "SIACUDEF": 
        [
            {"rama":"develop"},
            {"entorno":"Desarrollo,Validacion"},
            {"usuario":"lmunma1,pcasla1"},
			{"variable":"0"}
        ]
            }
}'''

                def props = readJSON text: jsonString
                def propsDos = readJSON text: jsonStringDos
                def propsTres = readJSON text: jsonStringTres
                def hash = props['hash']
                def coche = propsDos.cars['Ford'][0].model
                def coche2 = propsDos.cars['Ford'][0].doors
                def coche3 = propsDos.cars['Ford'][1].model
                def app1 = propsTres.apps['SIACUDEF'].rama
                def app2 = propsTres.apps.SIACUDEF.usuario
                echo "$hash"
                echo "$coche"   
                echo "$coche2"
                echo "$coche3"
                echo "$app1"
                echo "$app2"
               
            }
          }
    }
    }
}
