(defproject math-server "1.0.0-SNAPSHOT"
  :description "Simple math server for load testing demo"
  :dependencies [[org.clojure/clojure "1.3.0"]
		 [compojure "1.0.1"]
		 [incanter "1.2.4"]
                 [clj-http "0.2.7"]]
  :dev-dependencies [[lein-ring "0.5.4"]
                     [clj-stacktrace "0.2.4"]]
  :ring {:handler math.core/app}
  :extra-classpath-dirs [~(str (.toURI (java.io.File. "grinder/lib/*")))]
)
