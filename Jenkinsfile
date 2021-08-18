
def label = "JenkinsPOD-${UUID.randomUUID().toString()}"

def jenworkspace="/home/jenkins/workspace/${params.PROJECT}"

def mvnrepo="/tmp/repository"

def sharefile="/tmp/sharefile"

def k8srepo='/tmp/k8s_repos'


podTemplate(label: label, cloud: 'kubernetes',nodeSelector: 'devops.k8s.icjl/jenkins=jnlp',
    containers: [
        containerTemplate(
            name: 'jnlp',
            image: 'registry-vpc.cn-hangzhou.aliyuncs.com/hiningmeng/jnlp:v1',
            ttyEnabled: true,
            alwaysPullImage: false),
        containerTemplate(
            name: 'jnlp-maven',
            image: 'jenkins/jnlp-agent-maven',
            //image:'ungerts/jnlp-agent-maven',
            ttyEnabled: true,
            alwaysPullImage: false,
            command: 'cat')
    ],
    volumes: [
        hostPathVolume(hostPath: '/var/run/docker.sock', mountPath:'/var/run/docker.sock'),
        persistentVolumeClaim(mountPath: "$mvnrepo", claimName: 'maven-repo-pvc', readOnly: false),
        persistentVolumeClaim(mountPath: "$sharefile", claimName: 'sharefile-repo-pvc', readOnly: false),
    ]
)
{

    node (label) {
        stage('Hello World'){
            container('jnlp'){
                echo "hello, world"
                sh "ln -s $sharefile/kubectl  /usr/bin/kubectl"
                sh "ln -s $sharefile/docker /usr/bin/docker"

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
                    sh "mvn clean install -Dmaven.test.skip=true  -U  -s  $sharefile/settings.xml"
                }
            }
        }
        stage('Docker build'){
        }
        stage('K8S Deploy'){
        }
    }
}
