# Jetdotter: CLI to transform between JSON, EDN, YAML, and Transit

## Why jetdotter?

I made this quick babashka script in response to my inability to transform
[jet](https://github.com/borkdude/jet) to support YAML. The `dotter` suffix is
usually means to *daughter of* in Scandinavian culture.

# Installation

Download the jetdotter in the target folder and but somwhere on your paths.

## Usage

``` shell
ls *.json | xargs bb target/jetdotter --to yaml # find all json files and convert them to yaml
ls *.edn | xargs bb target/jetdotter --to transit # find all edn files and convert them to transit
```


## Compile

``` shell
make bb-script
```

Then add `#!/usr/local/bin/bb` on top of the script to run as a command.
