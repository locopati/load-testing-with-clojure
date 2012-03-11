(defproject cloud "1.0.0-SNAPSHOT"
  :description "Cloud-based agents for load testing"
  :dependencies [[org.cloudhoist/pallet "0.6.6"]
                 [org.cloudhoist/pallet-crates-all "0.5.0"]
                 [org.slf4j/slf4j-api "1.6.1"]
                 [ch.qos.logback/logback-core "1.0.0"]
                 [ch.qos.logback/logback-classic "1.0.0"]
                 [org.jclouds/jclouds-all "1.2.1"]
                 [org.jclouds/jclouds-compute "1.2.1"]
                 [org.jclouds.driver/jclouds-enterprise "1.2.1"]
                 [org.jclouds.driver/jclouds-jsch "1.2.1"]
                 [org.jclouds.driver/jclouds-slf4j "1.2.1"]]
  :repositories {"sonatype" "https://oss.sonatype.org/content/repositories/releases/"})