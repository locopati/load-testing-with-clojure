(ns cloud.core
  (:use [clojure.string :only [join]]
        [pallet.action directory remote-file exec-script]        
        [pallet core phase]
        [pallet.crate automated-admin-user])
  )

;;
;; Leiningen
;;

(defn lein-deps
  "This crate is a hack. It's easier to run lein as root and chown the final dir
  than to run as the desired user. The export is to get lein not to complain when
  running as root and the rest is to run lein deps in the correct dir. This
  should be doable using stevedore's chain-commands, but I wasn't able to make
  that work out."
  [session]
  (-> session
      (exec-script ~(join " "
                          ["export LEIN_ROOT=true &&"
                           "cd player/test/request &&"
                           "../../../bin/lein deps"]))))

(def lein-url
  "https://raw.github.com/technomancy/leiningen/stable/bin/lein")
  
(defn lein
  "Install Leiningen for the admin user"
  [session]
  (let [user (:username (admin-user session))]
    (-> session     
        (directory "bin" :owner user :group user)
        (remote-file "bin/lein" :url lein-url
                     :owner user :group user :mode "u+x")
        (exec-script ~(str "su - " user " -c 'bin/lein self-install'")))))

;;
;; Grinder agent crate
;;

(defn agent-daemon
  "Stop the Grinder agent if it is running and start it again"
  [session]
  (let [user (:username (:user session))]
    (-> session
        (exec-script ~(join " "                            
                            ["su -"
                             user
                             "-c"
                             "'"
                             (str "cd /home/" user "/player/test/request &&")
                             "bin/agent-stop.sh &&"
                             "RAILS_ENV=staging bin/agent-start.sh dev.junlabs.com"
                             "'"])))))

;;
;; Grinder agent node
;;

(defn create-agent-node
  "Spec for an amzn-linux node that is in the Relay security group"
  [] (node-spec :image {:os-family :amzn-linux
                        :image-id "us-east-1/ami-1b814f72"
                        :hardware-id "t1.micro"}
                :network {:security-groups ["relay"]}))

(defn create-agent-group
  "Spec for a grinder-agent node"
  [] (group-spec "grinder-agent"
                 :node-spec (create-agent-node)
                 :phases {:bootstrap (phase-fn
                                      (automated-admin-user)
                                      )                          
                          }))

(comment
  :configure (phase-fn
                                      (lein)))