pipeline {
    agent any  // Run on any available agent

    tools {
        maven "Maven" // Install the Maven version configured as "Maven" and add it to the path.
    }

    environment {
        PROJECT_DIR = "${WORKSPACE}"
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the Git repository to the workspace
                git url: 'https://github.com/Kavya-Goyal/Testing_Framework.git', branch: 'main'
            }
        }

        stage('Install Dependencies') {
            steps {
                // Run mvn clean install from the root directory of the project
                dir("${PROJECT_DIR}") {
                    script {
                        echo "Running mvn clean install"
                        runMaven("mvn clean install -DskipTests")
                    }
                }
            }
        }

        stage('Run API Tests') {
            steps {
                // Run the REST-assured API tests (Swagger Petstore suite)
                dir("${PROJECT_DIR}") {
                    script {
                        echo "Running API test suite"
                        runMaven("mvn clean test -DsuiteXmlFile=API-Testng.xml")
                    }
                }
            }
            post {
                always {
                    // Preserve the API report OUTSIDE target/ before the UI stage runs.
                    // The UI stage starts with `mvn clean`, which wipes target/ — and both
                    // suites write to the same target/extent-reports.html (see the
                    // ExtentSparkReporter path in TestListener / APITestListener), so
                    // without this copy the API report is silently lost.
                    // This runs in post/always so the report is kept even if a test fails.
                    script {
                        preserveApiReport()
                    }
                }
            }
        }

        stage('Run UI Tests') {
            steps {
                // Run the full Selenium UI regression suite
                dir("${PROJECT_DIR}") {
                    script {
                        echo "Running UI regression suite"
                        runMaven("mvn clean test -DsuiteXmlFile=testng.xml")
                    }
                }
            }
        }
    }

    post {
        always {
            // Publish the API report (copied to reports/ before the UI stage's clean)
            publishHTML([
                reportName: 'API Test Report',
                reportDir: 'reports',
                reportFiles: 'api-extent-reports.html',
                keepAll: false,
                allowMissing: true,
                alwaysLinkToLastBuild: true
            ])

            // Publish the UI report (still in target/, so its relative screenshot
            // paths — screenshots/... stripped of the target/ prefix in
            // TestListener.onTestFailure — keep resolving)
            publishHTML([
                reportName: 'UI Test Report',
                reportDir: 'target',
                reportFiles: 'extent-reports.html',
                keepAll: false,
                allowMissing: true,
                alwaysLinkToLastBuild: true
            ])

            // Clean workspace after build (publishHTML copies the reports first)
            cleanWs()
        }

        success {
            echo "Tests and reports were successfully generated!"
        }

        failure {
            echo "Tests failed. Please check the logs for details."
        }
    }
}

// Runs a Maven command on both Unix and Windows agents
def runMaven(String command) {
    if (isUnix()) {
        sh command
    } else {
        bat command
    }
}

// Copies target/extent-reports.html to reports/api-extent-reports.html, if it exists.
// No screenshots are attached to the API report, so moving it out of target/ does not
// break any relative asset paths the way moving the UI report would.
def preserveApiReport() {
    if (isUnix()) {
        sh 'mkdir -p reports && if [ -f target/extent-reports.html ]; then cp target/extent-reports.html reports/api-extent-reports.html; else echo "No API extent report found to preserve"; fi'
    } else {
        bat 'if not exist reports mkdir reports & if exist target\\extent-reports.html (copy /Y target\\extent-reports.html reports\\api-extent-reports.html) else (echo No API extent report found to preserve)'
    }
}
