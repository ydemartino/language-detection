This is a language detection library implemented in plain Java.
(aliases: language identification, language guessing)

  * Presentation : http://www.slideshare.net/shuyo/language-detection-library-for-java
  * ProjectHomeJa : Project Home in Japanese

# Abstract #

  * Generate language profiles from Wikipedia abstract xml
  * Detect language of a text using naive Bayesian filter
  * 99% over precision for 53 languages

# News #

  * 03/03/2014
    * Distribute a new package with short-text profiles (47 languages)
      * Build latest codes
      * Remove Apache Nutch's plugin (for API deprecation)
  * 01/12/2012
    * Migrate the repository of language-detection from subversion into git
      * for Maven support
  * 09/13/2011
    * Add language profile of Estonian, Lithuanian, Latvian and Slovene.
    * Support retrieving a list of loaded language profiles as getLangList() ( [issue 20](https://code.google.com/p/language-detection/issues/detail?id=20) )
    * support generating a language profile from plain text ( [issue 23](https://code.google.com/p/language-detection/issues/detail?id=23) )
    * Fixed a bug of [issue 21](https://code.google.com/p/language-detection/issues/detail?id=21).
  * 02/02/2011
    * fixed bugs (no profile directory / long text detectation)
  * 01/24/2011
    * 4x faster detection (thanks to [elmer.garduno](http://code.google.com/u/elmer.garduno/))
  * 12/22/2010
    * Support Apache Nutch's plugin
  * 11/18/2010
    * Provide a package file.

# Requires #

  * Java 1.8 or later

# Bibtex #

```
@misc{nakatani2010langdetect,
  title  = {Language Detection Library for Java},
  author = {Shuyo Nakatani}, 
  url    = {https://github.com/shuyo/language-detection},
  year   = {2010}
}
```

# Copyrights and License #

Copyright (c) 2010-2014 Cybozu Labs, Inc. All rights reserved.

> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at

> http://www.apache.org/licenses/LICENSE-2.0

> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.