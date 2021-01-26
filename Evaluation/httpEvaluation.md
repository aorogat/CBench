[<< Home](https://github.com/aelroby/CBench/)

### Evaluate a QA system  via http request
To evaluate your system, CBench will send a POST request to your QA system using the following URL
```
url?query=[question]&kb=[knowledge Base]
```
Where *_url_* is the one that your system is running on for example "http://www.aaa.com/QA/". CBench concatenates your *url* with the *Query string ?query=[question]&kb=[knowledge Base]* using the 2 parameters: *_query_* and *_kb_* that are set on the runtime. The *_query_* parameter value determined based on the current question from the chosen benchmark. For the *_kb_* parameter, you configure it at the beginning after running CBench to [default, dbpedia, wikidata or freebase]. *_default_* value means CBench uses the default benchmark for every file of questions. To add a new KG, read the next section.

For every question in the benchmark, CBench sends an http request to your QA system.

Example for the question *"In which films did Julia Roberts as well as Richard Gere play?"*
```url
http://www.aaa.com/QA/?query=In%20which%20films%20did%20Julia%20Roberts%20as%20well%20as%20Richard%20Gere%20play&kb=dbpedia
```
and expects the answers in a JSON file with the follwoing format
```json
{
    "questions":
            [{
                    "answers": [{
                            "head": {
                                "vars": ["films"]
                            },
                            "results": {
                                "bindings": [{
                                        "films": {
                                            "type": "uri",
                                            "value": "http://dbpedia.org/resource/Pretty_Woman"
                                        }
                                    }, {
                                        "films": {
                                            "type": "uri",
                                            "value": "http://dbpedia.org/resource/Runaway_Bride_(film)"
                                        }
                                    }]
                            }
                        }],
                    "Other keys": "Other values"
                }],
    "Other keys": "Other values"    
}
```
For Boolean Answer
```json
{
    "questions": [{
            "answers": [{
                    "head": {
                    },
                    "boolean": true
                }],
            "Other keys": "Other values"
        }],
    "Other keys": "Other values"
}
```
