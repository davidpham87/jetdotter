(ns edn-yaml.core
  (:require
   ["js-yaml" :as yaml]
   [cljs-bean.core :refer (->js)]
   [cljs-node-io.core :as io :refer [slurp]]
   [clojure.edn]
   [clojure.string :as str]
   [shadow.resource :as rc :refer-macros (inline)]))

(defn main [& args]
  (->
   #_(clojure.edn/read (inline (first args)))
   (slurp (first args))
   clojure.edn/read-string
   ->js
   yaml/dump
   println
   ))


(defn ^:dev/after-load reload []
  (println "reload"))

(comment
  (in-ns 'edn-yaml.core)
  )
