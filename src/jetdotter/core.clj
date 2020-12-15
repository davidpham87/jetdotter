(ns jetdotter.core
  (:require
   [cheshire.core :as j]
   [clj-yaml.core :as yaml]
   [clojure.edn]
   [clojure.java.io :refer (input-stream)]
   [clojure.pprint]
   [clojure.string :as str]
   [clojure.tools.cli :refer (parse-opts)]
   [cognitect.transit :as t])
  (:import java.io.ByteArrayOutputStream))

(def data-format #{:json :yml :edn})

(def cli-opts
  [["-f" "--from FROM" "Source Format. Only necessary for transit."]
   ["-t" "--to TO" (format "Target Format. One of %s."
                           (str/join ", " (map name data-format)))
    :default :edn
    :validate [format]]
   ["-h" "--help" "Show this help"]])

(defn extension [filename]
  (keyword (last (str/split filename #"\."))))

(defn convert-extension [filename target]
  (str (str/join "." (butlast (str/split filename #"\.")))
       "." (name target)))

(defn parse [s type]
  (case type
    :transit (t/read (t/reader (input-stream (.getBytes s "UTF-8")) :json))
    :yml (yaml/parse-string s)
    :yaml (parse s :yml)
    :json (j/decode s keyword)
    :edn (clojure.edn/read-string s)
    identity))

(defn generate [data type]
  (case type
    :transit (let [baos (ByteArrayOutputStream. 4096)
                   writer (t/writer baos :json)
                   _ (t/write writer data)]
               (String. (.toByteArray baos)))
    :yml (yaml/generate-string data)
    :yaml (generate data :yml)
    :json (j/encode data)
    :edn (str data)
    identity))

(defn -main [& args]
  (let [opts (parse-opts args cli-opts)]
    (when (get-in opts [:arguments :help])
      (println "Data to Data Conversion")
      (println (:summary opts)))
    (doseq [filename (get opts :arguments)]
      (let [source-format (or (get-in opts [:options :from]) (extension filename))
            target-format (get-in opts [:options :target])
            output-filename (convert-extension filename target-format)]
        (-> (slurp filename)
            (parse source-format)
            (generate target-format)
            (spit output-filename))))))
(comment
  (parse (generate {:a 3} :transit) :transit)
  (parse (generate {:a 3} :json) :json)
  (parse (generate {:a 3} :yaml) :yaml)
  (parse (generate {:a 3} :edn) :edn))
