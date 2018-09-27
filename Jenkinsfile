pipeline {
   agent any
   stages {
      stage('Compile Stage') {
         steps {
            echo 'Compiling project'
            sh 'mvn clean compile -DskipTests=true'
         }
      }
      stage('Test Stage') {
         steps {
            echo 'Running tests'
            sh 'mvn test'
         }
      }
      stage('Package Stage') {
      	steps {
            echo 'Packaging project'
      		sh 'mvn clean package'
      	}
      }
      stage('Deployment Stage') {
         steps {
            sh 'bash ./deploy.sh'
         }
      }
   }
}
