bb-script:
	bb -f src/jetdotter/core.clj --uberscript target/jetdotter
	bb -e '(let [f "target/jetdotter" s (slurp f)] (spit f "#!/usr/local/bin/bb\n") (spit f s :append true))'
	chmod +x target/jetdotter
