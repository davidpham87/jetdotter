#!/usr/local/bin/bb

;; Script to convert edn and yaml files

(ns edn-yaml.core
  (:require
   [clj-yaml.core :as yaml]
   [clojure.edn]
   [clojure.pprint]
   [clojure.string :as str]
   [clojure.tools.cli :refer [parse-opts]]))

(def cli-options
  ;; An option with a required argument
  [["-p" "--port PORT" "Port number"
    :default 80
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-h" "--help"]])

;; todo use cli
(defn extension [filename]
  (keyword (last (str/split filename #"\."))))

(defn convert-extension [filename]
  (let [extension (last (str/split filename #"\."))
        conversion-table {"edn" "yml" "yml" "edn"}]
    (str (str/join "." (butlast (str/split filename #"\.")))
         "." (conversion-table extension))))

(defn edn->yaml [filename]
  (let [output-name (convert-extension filename)
        data (-> filename slurp clojure.edn/read-string)]
    (->> data
         yaml/generate-string
         (spit output-name))))

(defn yaml->edn [filename]
  (let [output-name (convert-extension filename)
        data (-> filename slurp yaml/parse-string)]
    (clojure.pprint/pprint data (clojure.java.io/writer output-name))))

(defmulti convert extension)
(defmethod convert :default [filename]
  (println filename)
  (println "No extension find" (extension filename)))

(defmethod convert :edn [filename]
  (println "Convert to edn->yaml: " filename)
  (edn->yaml filename))

(defmethod convert :yaml [filename]
  (println "Convert to yaml->edn: " filename)
  (yaml->edn filename))

(defmethod convert :yml [filename]
  (println "Convert to yaml->edn: " filename)
  (yaml->edn filename))

(defn -main [& args]
  #_(:options (parse-opts args cli-options))
  (println args)
  (when (seq args)
    (doseq [filename args]
      (convert filename))))

(ns user (:require [edn-yaml.core]))
(apply edn-yaml.core/-main *command-line-args*)
