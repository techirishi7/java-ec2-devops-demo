pipeline {
  agent any
  environment {
    IMAGE_NAME = "YOUR_DOCKERHUB_USERNAME/java-ec2-demo:latest"
    DOCKERHUB_USER = "YOUR_DOCKERHUB_USERNAME"
    ANSIBLE_HOST_KEY_CHECKING = "False"
  }
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build and Test with Maven') {
      steps { sh 'mvn -B clean test package' }
    }
    stage('Build Docker Image') {
      steps { sh 'docker build -t $IMAGE_NAME .' }
    }
    stage('Push Image to Docker Hub') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds',
          usernameVariable: 'DH_USER', passwordVariable: 'DH_TOKEN')]) {
          sh '''
            echo "$DH_TOKEN" | docker login -u "$DH_USER" --password-stdin
            docker push "$IMAGE_NAME"
            docker logout
          '''
        }
      }
    }
    stage('Deploy to Three EC2 Servers') {
      steps {
        sh '''
          DOCKERHUB_USER="$DOCKERHUB_USER" ansible-playbook             -i ansible/inventory.ini ansible/deploy.yml             --private-key /var/lib/jenkins/.ssh/id_ed25519
        '''
      }
    }
  }
  post {
    always { junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true }
    success { echo 'Build and deployment completed. Verify all three URLs.' }
    failure { echo 'Pipeline failed. Inspect the failed stage Console Output.' }
  }
}