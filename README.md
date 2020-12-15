# Simple node js edn to yaml converter

## Installation

``` shell
npm install
shadow-cljs release app
nexe target/edn_yaml.js -o edn_yaml -t linux-x64 # or the target of your choice
mv edn_yaml ~/bin # to make it available on your path
```

## Usage

``` shell
edn_yaml file.edn # write file.yml
edn_yaml file1.edn file2.edn # write file1.yml and file2.yml
ls -r . | edn_yaml # find all edn and convert them to yaml
```
