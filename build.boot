(set-env!
 :source-paths #{"src"}
 :dependencies '[[adzerk/boot-cljs "0.0-2814-3" :scope "test"]
                 [adzerk/boot-reload "0.2.6"]
                 [adzerk/boot-test "1.0.4" :scope "test"]
                 [adzerk/bootlaces "0.1.11" :scope "test"]
                 [clj-fuzzy/clj-fuzzy "0.2.1"]
                 [org.clojure/clojurescript "0.0-3196"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [pandeiro/boot-http  "0.6.3-SNAPSHOT"]
                 [org.clojure/clojure "1.7.0-beta1" :scope "provided"]
                 ])

(require '[adzerk.bootlaces :refer [bootlaces! build-jar push-release]]
         '[adzerk.boot-cljs      :refer [cljs]]
         '[pandeiro.boot-http :refer :all]
         '[adzerk.boot-reload    :refer [reload]]
         '[adzerk.boot-test :refer [test]])

(def +version+ "0.0.1")

(bootlaces! +version+)

(task-options!
 pom  {:project     'johann/noname
       :version     +version+
       :description ""
       :url         ""
       :scm         {:url ""}
       :license     {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask run-cljs-test
  "Run cljs tests"
  []
  (fn middleware [next-handler]
    (fn handler [fileset]
      (sh "node" "target/script.js")
      (-> fileset next-handler))))

(deftask dev
  []
  (comp (watch)
        (speak)
        (cljs :source-map true :optimizations :none)
        (test)))

(deftask release []
  (comp (build-jar)
        (push-release)))
