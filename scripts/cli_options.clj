(require '[clojure.tools.cli :refer [parse-opts]])

(def cli-options
  ;; An option with a required argument
  [["-p" "--port PORT" "Port number"
    :default 80
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   [nil "--kebab-case flag" "Convert keys to kebab-case"
    :default false
    :parse-fn any?
    :validate [boolean? "must be either true or false"]]
   [nil "--snake-case flag" "Convert keys to snake_case"
    :default false
    :parse-fn any?
    :validate [boolean? "must be either true or false"]]
   ["-h" "--help" "Show this help"
    :default false]])

(let [opts (parse-opts *command-line-args* cli-options)]
  (println (:options opts))
  (when (get-in opts [:options :help])
    (println (:summary opts)))
  (when (:errors opts)
    (println (:errors opts))))
