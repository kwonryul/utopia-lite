(ns papercompany.utopia-lite.markdown
  (:require
   [markdown.core :as markdown]
   ["dompurify" :as DOMPurify]))

(set! *warn-on-infer* false)

(defn innerHTML [str]
  {:__html
   (.sanitize DOMPurify (markdown/md-to-html-string str))})
