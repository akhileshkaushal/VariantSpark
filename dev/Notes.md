
## Release to Maven Central

    export GPG_TTY=$(tty)
    mvn -DskipTests -P release deploy
    mvn nexus-staging:release
    
    
    
More info:  https://central.sonatype.org/pages/apache-maven.html#performing-a-release-deployment-with-the-maven-release-plugin

see: `~/.m2/settting.xml for all the passwords etc`


## Setting up an AWS vm for Hail compilation


Setup:

    sudo yum groupinstall 'Development Tools'
    sudo yum install java-1.8.0-openjdk-devel
    sudo yum install cmake
    sudo yum install gcc
    sudo yum install git
   
 
 Compilation and copy:
 
    ./gradlew -Dspark.version=2.3.0 clean shadowJar
    aws s3 cp build/libs/hail-all-spark.jar s3://variant-spark/deps/hail-0.1/hail-0.1-74bf1ebb3edc_2.3.0-all.jar