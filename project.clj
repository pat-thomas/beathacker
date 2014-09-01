(defproject beathacker "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main beathacker.core/main
  :dependencies [[org.clojure/clojure       "1.6.0"]
                 [memento                   "0.1.0"]
                 [javax.servlet/servlet-api "2.5"]
                 [compojure                 "1.1.8"]
                 [http-kit                  "2.1.16"]
                 [overtone                  "0.9.1"]
                 [org.clojure/tools.nrepl   "0.2.3"]
                 [org.clojure/core.async    "0.1.338.0-5c5012-alpha"]])
