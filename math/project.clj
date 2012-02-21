(defproject math-server "1.0.0-SNAPSHOT"
  :description "Simple math server for load testing demo"
  :dependencies [;; clojure
                 [org.clojure/clojure "1.2.1"]
                 ;; math server
		 [compojure "1.0.1"]
		 [incanter "1.2.4"]                 
                 [ring-bounce-favicon "0.1.0"]
                 ;; pallet
                 [org.cloudhoist/pallet "0.6.7"]
                 [org.cloudhoist/pallet-crates-standalone "0.5.0"]
                 [org.slf4j/slf4j-api "1.6.1"]
                 [ch.qos.logback/logback-core "1.0.0"]
                 [ch.qos.logback/logback-classic "1.0.0"]
                 [org.jclouds/jclouds-all "1.0.0"]
                 [org.jclouds.driver/jclouds-jsch "1.0.0"]
                 [org.jclouds.driver/jclouds-slf4j "1.0.0"]]
  :dev-dependencies [[clj-http "0.2.7"]
                     [lein-ring "0.5.4"]
                     [clj-stacktrace "0.2.4"]
                     [lein-git-deps "0.0.1-SNAPSHOT"] ;; experiment w vmfest
                     [net.sf.grinder/grinder "3.7.1"]]
  :repositories {"sonatype" "https://oss.sonatype.org/content/repositories/releases/"}
  :git-dependencies [["git://github.com/tbatchelli/vmfest.git" "develop"]]
  :ring {:handler math.core/app}
  ;; experiment w vmfest
  :extra-classpath-dirs [".lein-git-deps/vmfest/src"
                         ".lein-git-deps/vmfest/lib/*"]
)
