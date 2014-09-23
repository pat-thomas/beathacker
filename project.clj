(defproject beathacker "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main beathacker.server.core/main
  :dependencies [[org.clojure/clojure       "1.6.0"]
                 [memento                   "0.2.1"]
                 [javax.servlet/servlet-api "2.5"]
                 [compojure                 "1.1.8"]
                 [http-kit                  "2.1.16"]
                 [org.clojure/data.json     "0.2.5"]
                 [overtone                  "0.9.1"]
                 [org.clojure/tools.nrepl   "0.2.3"]
                 [org.clojure/core.async    "0.1.338.0-5c5012-alpha"]
                 [pat-thomas/clj-utils      "0.1.0"]

                 [org.clojure/clojurescript "0.0-2311"]
                 [om                        "0.7.1"]
                 [om-sync                   "0.1.1"]
                 [om-utils                  "0.1.0"]
                 [secretary                 "1.2.0"]
                 [sablono                   "0.2.6"]]

  :plugins [[lein-cljsbuild "1.0.3"]]
  :source-paths ["src"]
  :cljsbuild {:builds [{:id           "beathacker"
                        :source-paths ["src/beathacker/app"]
                        :compiler     {:output-to     "resources/public/js/compiled/beathacker.js"
                                       :output-dir    "resources/public/js/compiled/out"
                                       :optimizations :none
                                       :source-map    true}}]})
