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
