var __index = {"config":{"lang":["en"],"separator":"[\\s\\-,:!=\\[\\]()\"/]+|(?!\\b)(?=[A-Z][a-z])|\\.(?!\\d)|&[lg]t;","pipeline":["stemmer"]},"docs":[{"location":"index.html","title":"\u5e8f\u5217\u5316\u5b57\u6bb5","text":"<p>Serialize\u57fa\u4e8e\u817e\u8baf MMKV \u5b9e\u73b0, \u5176\u6bd4SharePreference/SQLite\u901f\u5ea6\u5feb, \u53ef\u4ee5\u6709\u6548\u89e3\u51b3ANR</p>"},{"location":"index.html#_1","title":"\u4f7f\u7528\u573a\u666f","text":"<p>\u672c\u9879\u76ee\u4e3a\u89e3\u51b3\u4ee5\u4e0b\u95ee\u9898</p> <ol> <li>\u53d8\u91cf\u53ea\u5b58\u5728\u5185\u5b58\u4e2d\u800c\u4e0d\u662f\u6620\u5c04\u672c\u5730\u78c1\u76d8</li> <li>\u53ea\u80fd\u5b58\u50a8\u57fa\u7840\u7c7b\u578b\u4e0d\u80fd\u76f4\u63a5\u5b58\u50a8\u5bf9\u8c61</li> <li>\u8bfb\u5199\u672c\u5730\u6570\u636e\u4f1a\u963b\u585e\u4e3b\u7ebf\u7a0b</li> <li>\u4e0d\u80fd\u76d1\u542c\u5b57\u6bb5\u7684\u8bfb\u5199</li> </ol> <p>\u5f3a\u5236\u9605\u8bfb</p> <p>\u8bf7\u4e00\u5b9a\u9605\u8bfb\u7ae0\u8282: \u5e38\u89c1\u95ee\u9898, \u4ee5\u4fdd\u8bc1\u6570\u636e\u5b89\u5168\u6027</p> <p>\u5173\u7cfb\u67e5\u8be2/\u5217\u8868\u6570\u636e/\u5927\u91cf\u6570\u636e\u573a\u666f\u5efa\u8bae\u4f7f\u7528\u6570\u636e\u5e93, \u672c\u6846\u67b6\u65e0\u6cd5\u6ee1\u8db3\u5176\u590d\u6742\u9700\u6c42</p>"},{"location":"index.html#_2","title":"\u5e8f\u5217\u5316\u5b57\u6bb5","text":"<p>\u521b\u5efa\u4e00\u4e2a\u53d8\u91cf, \u5bf9\u8be5\u53d8\u91cf\u8bfb\u5199\u90fd\u4f1a\u81ea\u52a8\u6620\u5c04\u5230\u672c\u5730\u78c1\u76d8, \u5373\u5e8f\u5217\u5316\u5b57\u6bb5</p> <pre><code>@SerializeConfig(mmapID = \"app_config\") // \u6307\u5b9ammapID\u53ef\u4ee5\u907f\u514d\u5b57\u6bb5\u540d\u91cd\u590d\u60c5\u51b5\u4e0b\u5bfc\u81f4\u7684\u9519\u8bef\nobject AppConfig {\nvar name: String by serial()\nvar simple: String by serial(\"\u9ed8\u8ba4\u503c\", \"\u81ea\u5b9a\u4e49\u952e\u540d\")\n}\n</code></pre> <p>\u4e4b\u540e\u8be5\u5b57\u6bb5\u8bfb\u5199\u90fd\u4f1a\u81ea\u52a8\u8bfb\u53d6\u548c\u5199\u5165\u5230\u672c\u5730\u78c1\u76d8</p> <pre><code>AppConfig.name = \"\u540d\u79f0\" // \u5199\u5165\u5230\u672c\u5730\u78c1\u76d8\nAppConfig.name // \u8bfb\u53d6\u81ea\u672c\u5730\u78c1\u76d8\n</code></pre> <p>\u521d\u59cb\u5316</p> <p>\u591a\u8fdb\u7a0b\u9879\u76ee\u8981\u6c42\u5728<code>Application</code>\u4e2d\u521d\u59cb\u5316MMKV <pre><code>MMKV.initialize(this)\n</code></pre></p>"},{"location":"index.html#_3","title":"\u6570\u636e\u7c7b\u578b","text":"<p>\u652f\u6301\u5b58\u50a8\u7684\u7c7b\u578b\u53d6\u51b3\u4e8e<code>SerializeHook</code>, \u5982\u679c\u5f00\u53d1\u8005\u6ca1\u6709\u81ea\u5b9a\u4e49SerializeHook\u90a3\u4e48\u4ec5\u652f\u6301\u4ee5\u4e0b\u7c7b\u578b</p> \u7c7b\u578b \u63cf\u8ff0 \u57fa\u7840\u7c7b\u578b \u4efb\u4f55\u57fa\u7840\u7c7b\u578b Serializable Serializable\u5b50\u7c7b Parcelable Parcelable\u5b50\u7c7b \u96c6\u5408/\u6570\u7ec4 \u4ee5\u4e0a\u7c7b\u578b\u7684\u96c6\u5408/\u6570\u7ec4, \u8bf7\u586b\u5199\u6b63\u786e\u6cdb\u578b, \u5426\u5219\u629b\u51fa<code>ClassCastException</code> <p>\u57fa\u7840\u7c7b\u578b\u9ed8\u8ba4\u503c</p> <p>\u672c\u6846\u67b6\u57fa\u4e8ekotlin\u8bbe\u8ba1, \u57fa\u7840\u7c7b\u578b\u9ed8\u8ba4\u503c\u4e5f\u662fnull</p>"},{"location":"index.html#_4","title":"\u53ef\u7a7a\u5b57\u6bb5","text":"<p>\u5982\u679c\u4e0d\u58f0\u660e\u53ef\u7a7a\u7c7b\u578b, \u4e14\u4e5f\u4e0d\u5b58\u5728\u9ed8\u8ba4\u503c, \u90a3\u4e48\u8bfb\u53d6\u4e0d\u5b58\u5728\u7684\u5b57\u6bb5\u65f6\u5c06\u629b\u51fa\u5f02\u5e38</p> <pre><code>var nameOrNull: String? by serial()   // \u4e0d\u5b58\u5728\u65f6\u8bfb\u53d6\u4e3anull\nvar nameOrException: String by serial()  // \u4e0d\u5b58\u5728\u65f6\u8bfb\u53d6\u5c06\u5d29\u6e83\n</code></pre>"},{"location":"index.html#_5","title":"\u61d2\u52a0\u8f7d","text":"<p>\u5373\u53ea\u5728\u7b2c\u4e00\u6b21\u8bfb\u53d6\u5b57\u6bb5\u7684\u65f6\u5019\u624d\u4f1a\u4ece\u672c\u5730\u8bfb\u53d6, \u540e\u7eed\u4ece\u5185\u5b58\u8bfb\u53d6</p> <pre><code>var model: ModelSerializable by serialLazy() // \u61d2\u52a0\u8f7d\n</code></pre> <ol> <li>\u4e0d\u652f\u6301\u8de8\u8fdb\u7a0b</li> <li>\u5199\u5165\u65f6\u5c06\u5148\u66f4\u6539\u5185\u5b58\u503c, \u7136\u540e\u4f7f\u7528\u5f02\u6b65\u65b9\u5f0f\u5199\u5165\u78c1\u76d8</li> </ol> <p>\u96f6\u8017\u65f6</p> <p>\u5f7b\u5e95\u89e3\u51b3\u53cd\u590d\u8bfb\u53d6\u78c1\u76d8\u9020\u6210\u8017\u65f6</p>"},{"location":"index.html#_6","title":"\u52a8\u6001\u952e\u540d","text":"<p>\u52a8\u6001\u952e\u540d\u8bf7\u4f7f\u7528<code>{}</code>\u51fd\u6570\u56de\u8c03\u8fd4\u56de\u503c</p> <pre><code>var userId: String = \"0123\"\nvar balance: String by serial(\"0.0\", { \"${userId}:balance\" })\n</code></pre>"},{"location":"arguments.html","title":"\u9875\u9762\u53c2\u6570","text":"<p>\u5feb\u901f\u5728Activity/Fragment\u4e2d\u4f20\u9012\u548c\u63a5\u53d7\u53c2\u6570</p> <p>\u9ed8\u8ba4\u503c</p> <p>\u57fa\u7840\u7c7b\u578b\u9ed8\u8ba4\u503c\u4e3anull(\u672a\u6307\u5b9a\u9ed8\u8ba4\u503c\u60c5\u51b5\u4e0b), \u8bf7\u6ce8\u610f\u975e\u53ef\u7a7a\u7c7b\u578b\u8bfb\u5230null\u4f1a\u5d29\u6e83</p>"},{"location":"arguments.html#_1","title":"\u4f20\u9012\u53c2\u6570","text":"<p>\u5728\u754c\u9762A\u4f20\u9012</p> ActivityFragment <pre><code>openActivity&lt;TestActivity&gt;(\n\"parcelize\" to ModelParcelable(),\n\"name\" to \"\u540d\u79f0\",\n\"age\" to 34\n)\n</code></pre> <pre><code>MyFragment().withArguments(\n\"parcelize\" to ModelParcelable(),\n\"name\" to \"\u540d\u79f0\",\n\"age\" to 34\n)\n</code></pre>"},{"location":"arguments.html#_2","title":"\u8bfb\u53d6\u53c2\u6570","text":"<p>\u5728\u754c\u9762B\u8bfb\u53d6</p> <pre><code>private val parcelize: ModelParcelable by bundle()\nprivate val name:String by bundle()\nprivate val age:Int by bundle()\n</code></pre> <ol> <li>\u5141\u8bb8\u5b57\u6bb5\u4e3a\u4efb\u4f55\u8bbf\u95ee\u6743\u9650(\u4f8b\u5982private/public)</li> <li>\u4e0d\u4f7f\u7528<code>openActivity</code>\u7b49\u51fd\u6570\u4e5f\u53ef\u4ee5\u63a5\u53d7\u6570\u636e</li> </ol>"},{"location":"arguments.html#_3","title":"\u96c6\u5408\u6cdb\u578b","text":"<p>\u5199\u5165<code>List&lt;String&gt;</code>,  \u8bfb\u53d6\u5c31\u4e00\u5b9a\u662f<code>List&lt;String&gt;</code>, \u5426\u5219\u96c6\u5408<code>get[]</code>\u65f6\u4f1a\u629b\u51fa<code>ClassCastException</code></p>"},{"location":"config.html","title":"\u5e8f\u5217\u5316\u914d\u7f6e","text":""},{"location":"config.html#_1","title":"\u5355\u4f8b\u914d\u7f6e","text":"<p>\u4e3a\u7c7b\u6dfb\u52a0\u6ce8\u89e3<code>@SerializeConfig</code>\u53ef\u6307\u5b9a\u5176\u6240\u6709<code>serialXX()</code>\u5b57\u6bb5\u7684MMKV\u5b9e\u4f8b\u914d\u7f6e</p> <pre><code>@SerializeConfig(mmapID = \"app_config\")\nobject AppConfig {\nvar isFirstLaunch: Boolean by serial()\n}\n</code></pre> <p>\u540c\u540d\u8986\u76d6</p> <p>\u5f53<code>mmapID</code>\u548c\u5b57\u6bb5\u540d\u76f8\u540c\u60c5\u51b5\u4e0b\u4f1a\u8986\u76d6\u503c, \u751a\u81f3\u5f15\u8d77\u8bfb\u53d6\u7c7b\u578b\u9519\u8bef\u5bfc\u81f4\u5d29\u6e83</p>"},{"location":"config.html#_2","title":"\u5168\u5c40\u914d\u7f6e","text":"<pre><code>Serialize.mmkv = MMKV.mmkvWithID(\"app_config\")\n</code></pre>"},{"location":"config.html#_3","title":"\u591a\u8d26\u6237\u5b58\u50a8","text":"<p>\u4f7f\u7528<code>SerializeHook</code>\u52a8\u6001\u6307\u5b9a<code>mmapID</code>, \u5e38\u89c1\u573a\u666f\u4e3a\u6839\u636e\u8d26\u53f7\u4f7f\u7528\u4e0d\u540c\u7528\u6237\u6570\u636e</p> <p>\u521b\u5efa\u7528\u6237\u6570\u636e\u7c7b <pre><code>@SerializeConfig(mmapID = \"user_config\") // \u6307\u5b9ammapID\u53ef\u4ee5\u907f\u514d\u5b57\u6bb5\u540d\u91cd\u590d\u60c5\u51b5\u4e0b\u5bfc\u81f4\u7684\u9519\u8bef\nobject UserConfig {\nvar userId: String by serialLazy()\n}\n</code></pre></p> <p>\u5b9e\u73b0<code>SerializeHook</code> <pre><code>class ProtobufSerializeHook : SerializeHook {\n// ...\noverride fun mmkvWithID(mmapID: String, mode: Int, cryptKey: String?): MMKV {\n// \u5f53\u5b58\u50a8\u4e3a\u7528\u6237\u6570\u636e\u65f6, \u6dfb\u52a0\u5f53\u524d\u8d26\u6237\u5230mmapID\u4e2d, \u8bf7\u6ce8\u610f\u4e0d\u8981\u5faa\u73af\u8c03\u7528\nif (mmapID == \"user_config\") {\nreturn super.mmkvWithID(mmapID + \"_\" + AppConfig.currentAccount, mode, cryptKey)\n}\nreturn super.mmkvWithID(mmapID, mode, cryptKey)\n}\n}\n</code></pre></p>"},{"location":"config.html#_4","title":"\u6307\u5b9a\u5b58\u50a8\u76ee\u5f55/\u65e5\u5fd7\u7b49\u7ea7","text":"<p>\u9ed8\u8ba4\u60c5\u51b5\u4e0b\uff0cMMKV \u5c06\u6587\u4ef6\u5b58\u50a8\u5728<code>$(FilesDir)/mmkv/</code></p> <p>\u4f7f\u7528MMKV\u65b9\u6cd5\u53ef\u6307\u5b9a\u81ea\u5b9a\u4e49\u5b58\u50a8\u76ee\u5f55\u6216\u65e5\u5fd7\u8f93\u51fa\u7b49\u7ea7</p> <pre><code>MMKV.initialize(cacheDir.absolutePath, MMKVLogLevel.LevelInfo) // \u5b58\u50a8\u8def\u5f84, \u65e5\u5fd7\u7b49\u7ea7\n</code></pre>"},{"location":"config.html#_5","title":"\u6e05\u9664\u6570\u636e","text":"<p>\u4e24\u79cd\u65b9\u5f0f\u6e05\u9664\u6570\u636e</p> <ol> <li> <p>\u8d4b\u503c\u4e3anull     <pre><code>var userId :String by serial()\nuserId = null\n</code></pre></p> </li> <li> <p>\u6307\u5b9a<code>MMKV\u5b9e\u4f8b</code>\u5220\u9664     <pre><code>MMKV.defaultMMKV().remove(\"\u5b57\u6bb5\u540d\")\nMMKV.defaultMMKV().clearAll()\nMMKV.mmkvWithID(\"app_config\").clearAll()\n</code></pre></p> </li> </ol>"},{"location":"function.html","title":"\u5e8f\u5217\u5316\u51fd\u6570","text":"<p>\u901a\u8fc7\u51fd\u6570\u8bfb\u5199\u6570\u636e, \u65b9\u5f0f\u6bd4\u8f83\u539f\u59cb</p> <pre><code>serialize(\"name\" to \"\u540d\u79f0\", \"\u5e74\u9f84\" to 30) // \u5199\nval firstName:String = deserialize(\"firstName\") // \u8bfb\nval lastName:String = deserialize(\"lastName\", \"\u9ed8\u8ba4\u503c\") // \u8bfb\u53d6\u5931\u8d25\u8fd4\u56de\u9ed8\u8ba4\u503c\nval username:String = MMKV.mmkvWithID(\"User\").deserialize(\"name\") // \u6307\u5b9ammapID/\u6570\u636e\u9694\u79bb\n</code></pre> <p>\u4e0d\u63a8\u8350</p> <p>\u4ee3\u7801\u53ef\u8bfb\u6027\u5dee, \u4e14\u5bb9\u6613\u8bef\u5199, \u4e0d\u4fbf\u4e8e\u7edf\u4e00\u7ba1\u7406</p>"},{"location":"hook.html","title":"\u81ea\u5b9a\u4e49\u5e8f\u5217\u5316","text":"<p> \u5f3a\u70c8\u5efa\u8bae</p> <p>\u4f7f\u7528\u81ea\u5b9a\u4e49\u5e8f\u5217\u5316, \u53ef\u4ee5\u907f\u514d\u8bfb\u53d6\u65e7\u6570\u636e\u5931\u8d25\u95ee\u9898</p> <p>\u6240\u6709\u6570\u636e\u7684\u5e8f\u5217\u5316/\u53cd\u5e8f\u5217\u5316\u90fd\u4f1a\u7ecf\u8fc7<code>SerializeHook</code>\u8f6c\u6362, \u5728\u5b57\u8282\u6570\u7ec4/\u5bf9\u8c61\u4e4b\u95f4</p> <pre><code>Serialize.hook = ProtobufSerializeHook()\n</code></pre> SerializeHook <pre><code>interface SerializeHook {\n/** \u4f7f\u7528[Parcelable]/[Serializable]\u5e8f\u5217\u5316\u65b9\u6848 */\ncompanion object DEFAULT : SerializeHook\n/**\n     * \u5e8f\u5217\u5316\u5b57\u6bb5\n     *\n     * @param name \u5b57\u6bb5\u540d\n     * @param type \u5b57\u6bb5\u7c7b\u578b\n     * @param data \u5b57\u6bb5\u5b9e\u4f8b\n     *\n     * @return \u5b58\u50a8\u5b57\u8282\u6570\u7ec4, \u5982\u679c\u8fd4\u56denull\u5219\u5c06\u5220\u9664\u8be5\u5b57\u6bb5\n     */\nfun &lt;T&gt; serialize(name: String, type: Class&lt;T&gt;, data: Any): ByteArray? {\n}\n/**\n     * \u53cd\u5e8f\u5217\u5316\u5b57\u6bb5\n     *\n     * @param name \u5b57\u6bb5\u540d\n     * @param type \u5b57\u6bb5\u7c7b\u578b\n     * @param bytes \u5b57\u6bb5\u5b57\u8282\u7801\n     *\n     * @return \u53cd\u5e8f\u5217\u5316\u540e\u7684\u5bf9\u8c61, \u5982\u679c\u8fd4\u56denull\u5219\u5c06\u4f7f\u7528\u9ed8\u8ba4\u503c(\u5b58\u5728\u7684\u8bdd)\n     */\nfun &lt;T&gt; deserialize(name: String, type: Class&lt;T&gt;, bytes: ByteArray): Any? {\n}\n/**\n     * \u6839\u636emmapID\u83b7\u53d6mmkv\u5b9e\u4f8b\n     */\nfun mmkvWithID(mmapID: String, mode: Int, cryptKey: String?): MMKV {\nreturn MMKV.mmkvWithID(mmapID, mode, cryptKey, null)\n}\n}\n</code></pre> <p>\u53c2\u8003\u793a\u4f8b</p> <ol> <li>JsonSerializeHook</li> <li>ProtobufSerializeHook</li> </ol> <p>\u52a0\u5bc6\u672c\u5730\u6570\u636e</p> <p>MMKV\u652f\u6301\u52a0\u5bc6, \u4e0d\u9700\u8981\u4f7f\u7528<code>SerializeHook</code>, \u8bf7\u81ea\u884c\u641c\u7d22\u5b9e\u73b0</p>"},{"location":"issues.html","title":"\u5e38\u89c1\u95ee\u9898","text":""},{"location":"issues.html#_1","title":"\u8d4b\u503c\u65e0\u6548","text":"<pre><code>// \u8981\u6c42\u4fee\u6539UserData\u5b57\u6bb5, \u7136\u540e\u4fdd\u5b58\u5230\u672c\u5730\nobject UserConfig {\nvar userData:UserData by serial()\n}\n</code></pre> <p>\u9519\u8bef\u793a\u4f8b <pre><code>UserConfig.userData.name = \"new name\"\nUserConfig.userData = UserConfig.userData\n</code></pre> \u5982\u679c\u4f7f\u7528serialLazy\u4ee5\u4e0a\u65b9\u5f0f\u6709\u6548(\u56e0\u4e3a\u4ed6\u81ea\u5e26\u4e34\u65f6\u53d8\u91cf)</p> <p>\u4fee\u6539\u65e0\u6548</p> <p>\u4fee\u6539\u53d8\u91cf\u91cc\u9762\u7684\u5b57\u6bb5\u5e76\u4e0d\u4f1a\u66f4\u65b0\u672c\u5730\u6570\u636e, \u4e0b\u6b21\u8bfb\u53d6\u7684\u8fd8\u662f\u65e7\u7684\u503c</p> <p>\u89e3\u51b3\u529e\u6cd5, \u4f7f\u7528\u4e34\u65f6\u53d8\u91cf</p> <pre><code>val userData = UserConfig.userData\nuserData.name = \"new name\"\nUserConfig.userData = userData\n</code></pre>"},{"location":"issues.html#_2","title":"\u8bfb\u53d6\u65e7\u6570\u636e\u5d29\u6e83","text":"<p>\u5982\u679c\u5f00\u53d1\u8005\u672a\u81ea\u5b9a\u4e49SerializeHook\u800c\u4f7f\u7528\u9ed8\u8ba4\u5e8f\u5217\u5316, \u5f88\u53ef\u80fd\u5bfc\u81f4\u5371\u9669\u6570\u636e</p> <p>\u6570\u636e\u635f\u574f</p> <p>\u589e\u5220\u5b57\u6bb5\u53ef\u80fd\u5bfc\u81f4\u65e0\u6cd5\u8bfb\u53d6\u6570\u636e, \u7531\u4e8e<code>Serializable</code>\u548c<code>Parcelable</code>\u672c\u8eab\u5c40\u9650\u5bfc\u81f4</p> <p>\u89e3\u51b3\u529e\u6cd5\u662f\u81ea\u5b9a\u4e49<code>SerializeHook</code>, \u4f7f\u7528<code>Json/Protobuf</code>\u7b49\u5e8f\u5217\u5316\u6846\u67b6\u5b9e\u73b0\u6570\u636e\u5b58\u50a8</p> <p>Serializable</p> <ol> <li>\u589e\u5220\u5b57\u6bb5\u5bfc\u81f4\u8bfb\u53d6\u5931\u8d25, \u4f7f\u7528<code>serialVersionUUID</code>\u53ef\u89e3\u51b3</li> <li>\u4f46\u65b0\u589e\u5b57\u6bb5\u9ed8\u8ba4\u503c\u5c06\u4e3a\u96f6\u503c\u6216null, \u800c\u4e0d\u662f\u58f0\u660e\u7684\u9ed8\u8ba4\u503c</li> </ol> <p>Parcelable</p> <ol> <li>\u8bfb\u53d6\u65b0\u589e\u7684\u975e\u7a7a\u7c7b\u578b\u5b57\u6bb5\u4f1a\u5d29\u6e83(\u5982\u679c\u4e0d\u5b58\u5728)</li> <li>\u5b57\u6bb5\u987a\u5e8f\u88ab\u6253\u4e71\u4f1a\u5bfc\u81f4\u8bfb\u53d6\u5931\u8d25</li> </ol>"},{"location":"issues.html#_3","title":"\u5e8f\u5217\u5316\u51fd\u6570\u7c7b\u578b","text":"<p>\u4e00\u4e9b\u5e8f\u5217\u5316\u6846\u67b6\u4e0d\u652f\u6301\u5305\u542b\u51fd\u6570\u7c7b\u578b, \u4f8b\u5982<code>kotlin-serialization</code>, \u6dfb\u52a0<code>@Transient</code>\u5373\u53ef</p> <pre><code>@Serializable\nclass Data(var name: String, @Transient var unit: () -&gt; Unit)\n</code></pre>"},{"location":"issues.html#_4","title":"\u8fc1\u79fb\u65e7\u6570\u636e","text":"<ol> <li> <p>\u4ee5\u524d\u4f7f\u7528MMKV, \u7531\u4e8e\u672c\u9879\u76ee\u57fa\u4e8eMMKV\u6240\u4ee5\u4e0d\u9700\u8981\u8fc1\u79fb</p> </li> <li> <p>\u4ee5\u524d\u4f7f\u7528SharedPreferences, \u53ef\u4ee5\u4f7f\u7528MMKV\u8fc1\u79fb\u65b9\u6cd5</p> <pre><code>MMKV.defaultMMKV().importFromSharedPreferences(getSharedPreferences(\"sp\", MODE_PRIVATE))\n</code></pre> </li> </ol> <p>\u66f4\u591a\u6570\u636e\u8fc1\u79fb\u9700\u6c42\u8bf7\u5b9e\u73b0<code>SerializeHook</code>\u63a5\u53e3\u6765\u81ea\u5b9a\u4e49</p>"},{"location":"liveData.html","title":"\u5e8f\u5217\u5316LiveData","text":"<p>\u4f7f\u7528<code>serialLiveData</code>\u521b\u5efaLiveData</p> <pre><code>val liveData by serialLiveData(\"\u9ed8\u8ba4\u503c\")\n</code></pre> <p>\u6bcf\u6b21\u5199\u5165\u90fd\u56de\u8c03\u89c2\u5bdf\u8005<code>observe</code> <pre><code>liveData.observe(this) {\ntoast(\"\u89c2\u5bdf\u5230\u672c\u5730\u6570\u636e: $it\")\n}\n</code></pre></p>"},{"location":"updates.html","title":"\u66f4\u65b0\u65e5\u5fd7","text":""},{"location":"updates.html#131","title":"1.3.1","text":"<ul> <li>fix: lazyField\u7ebf\u7a0b\u5b89\u5168</li> </ul>"},{"location":"updates.html#130","title":"1.3.0","text":"<ul> <li>\u65b0\u589eserialLiveData\u53ef\u89c2\u5bdf\u5e8f\u5217\u5316\u6570\u636e</li> <li>\u7981\u7528\u672c\u5730\u53d8\u91cf\u4f7f\u7528serial</li> </ul>"},{"location":"updates.html#123","title":"1.2.3","text":"<ul> <li>serialLazy\u4f7f\u7528\u5f02\u6b65\u7ebf\u7a0b\u5199\u5165\u78c1\u76d8, \u8be5\u59d4\u6258\u5c5e\u6027\u57fa\u672c\u5b9e\u73b0\u548c\u5185\u5b58\u5b57\u6bb5\u8bfb\u5199\u540c\u7b49\u6548\u7387</li> <li>\u51fd\u6570\u5b9e\u73b0\u4f18\u5316</li> <li>\u66f4\u65b0\u51fd\u6570\u6ce8\u91ca</li> </ul>"}]}