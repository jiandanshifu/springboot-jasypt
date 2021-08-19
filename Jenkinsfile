
def label = "JenkinsPOD-${UUID.randomUUID().toString()}"

def jenworkspace="/home/jenkins/workspace/${params.PROJECT}"

def mvnrepo="/tmp/repository"

def sharefile="/tmp/sharefile"

def k8srepo='/tmp/k8s_repos'


podTemplate(label: label, cloud: 'kubernetes',nodeSelector: 'devops.k8s.icjl/jenkins=jnlp',
    containers: [
        containerTemplate(
            name: 'jnlp',
            image: 'registry.cn-beijing.aliyuncs.com/kubesphereio/jnlp-slave:3.27-1',
            tty: true,
            alwaysPullImage: true),
        containerTemplate(
            name: 'jnlp-maven',
            image: 'jenkins/jnlp-agent-maven:latest',
            //image:'ungerts/jnlp-agent-maven',
            tty: true,
            alwaysPullImage: true,
            command: '')
    ],
    volumes: [
        hostPathVolume(hostPath: '/var/run/docker.sock', mountPath:'/var/run/docker.sock')
    ]
)
{

    node (label) {
        stage('Hello World'){
            container('jnlp-maven'){
                echo "hello, world"

            }
        }
        stage('Git Pull'){
            dir("$jenworkspace"){
                git branch: "${params.BRANCH}", changelog: false, credentialsId: 'jenkins-pull-key', poll: false, url: "${params.CODE_URL}"
            }
        }
        stage('Mvn Package'){
            container('jnlp-maven'){
                dir("$jenworkspace"){
                    sh "mvn clean install -Dmaven.test.skip=true"
                }
            }
        }
        stage('Docker build'){
        }
        stage('K8S Deploy'){
        }
    }
}
