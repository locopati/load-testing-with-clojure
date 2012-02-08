(defproject math-server "1.0.0-SNAPSHOT"
  :description "Simple math server for load testing demo"
  :dependencies [[org.clojure/clojure "1.3.0"]
		 [compojure "1.0.1"]
		 [incanter "1.2.4"]                 
                 [ring-bounce-favicon "0.1.0"]]
  :dev-dependencies [[clj-http "0.2.7"]
                     [lein-ring "0.5.4"]
                     [lein-git-deps "0.0.1-SNAPSHOT"]
                     [clj-stacktrace "0.2.4"]
                     [net.sf.grinder/grinder "3.7.1"]]
  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"}
  :git-dependencies [["git://github.com/tbatchelli/vmfest.git" "develop"]]
  :ring {:handler math.core/app}
  :extra-classpath-dirs [".lein-git-deps/vmfest/src"
                         ".lein-git-deps/vmfest/lib/*"]
)
