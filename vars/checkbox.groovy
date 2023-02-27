pipeline{
    agent any
    stages {
        stage("Release scope") {
            steps {
                script {
                    // This list is going to come from a file, and is going to be big.
                    // for example purpose, I am creating a file with 3 items in it.
                    sh "echo \"first\nsecond\nthird\" > ${WORKSPACE}/list"

                    // Load the list into a variable
                    env.LIST = readFile("${WORKSPACE}/list").replaceAll("\n", ",")

                    env.RELEASE_SCOPE = input message: 'User input required', ok: 'Release!',
                            parameters: [extendedChoice(
                            name: 'ArchitecturesCh',
                            defaultValue: "${env.BUILD_ARCHS}",
                            multiSelectDelimiter: ',',
                            type: 'PT_CHECKBOX',
                            value: env.LIST
                      )]
                      // Show the select input
                      env.RELEASE_SCOPE = input message: 'User input required', ok: 'Release!',
                            parameters: [choice(name: 'CHOOSE_RELEASE', choices: env.LIST, description: 'What are the choices?')]
                }
                echo "Release scope selected: ${env.RELEASE_SCOPE}"
            }
        }
    }
}
