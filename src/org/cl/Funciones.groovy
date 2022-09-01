//Funciones.groovy

//Classpath
package org.cl

def unirDosStrings(String param1, String param2){
	return param1 + param2
}

def mostrarNombre(){
	def request = libraryResource 'org/cl/nombres.json'
	def json    = readJSON text: request

	return json.nombre
}

def leerJson(){
	def dataJson = readJSON file: 'nombres.json'

		echo "zapData = ${dataJson}"

		def servidor1Data = dataJson.servidor1
		echo "servidor1Data = ${servidor1Data}"
}
return this;
