# MarkdownView

Android library to display markdown text.

It uses [Flexmark](https://github.com/vsch/flexmark-java) and some of its extensions.

## Setup

Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency:
```gradle
compile 'com.github.tiagohm:MarkdownView:LATEST-VERSION'
```

LATEST-VERSION is [![](https://jitpack.io/v/tiagohm/MarkdownView.svg)](https://jitpack.io/#tiagohm/MarkdownView)

## Usage

```xml
<br.tiagohm.markdownview.MarkdownView
 android:id="@+id/markdown_view"
 app:escapeHtml="false"
 android:layout_width="match_parent"
 android:layout_height="match_parent"/>
```
```java
mMarkdownView = (MarkdownView)findViewById(R.id.mark_view);
mMarkdownView.loadMarkdownFromAsset("markdown1.md", MarkdownView.Styles.GITHUB);
//or
mMarkdownView.loadMarkdown("**MarkdownView**", MarkdownView.Styles.GITHUB);
```

## Themes
* GitHub
* Electron

## Support

- [x] Bold `**Text**` or `__Text__`
- [x] Italic `*Text*` or `_Text_`
- [x] Strikethrough `~~Text~~`
- [x] Horizontal Rules `---`
- [x] Headings `#`
- [x] Links `[alt](url)`
- [x] Images `![alt](url)`
- [x] Code
- [x] Blockquote
- [x] Nested Blockquote
- [x] Lists
- [x] Tables
- [x] TaskList
- [x] AutoLink
- [x] Abbreviation
- [x] Mark `==Text==`
- [x] Subscript `H~2~O`
- [x] Superscript `10^-10^`
- [x] Keystroke `@ctrl+alt+del@`
- [x] MathJax `$x = {-b \pm \sqrt{b^2-4ac} \over 2a}$`
- [x] Footnotes
- [x] Image Resizing `![alt](url@100px|auto)`
- [x] Syntax Highlighting (using [Highlight.js](https://highlightjs.org/))

<img width='380' src='https://raw.githubusercontent.com/tiagohm/MarkdownView/master/1.png'/>
<img width='380' src='https://raw.githubusercontent.com/tiagohm/MarkdownView/master/2.png'/>
<img width='380' src='https://raw.githubusercontent.com/tiagohm/MarkdownView/master/3.png'/>
<img width='380' src='https://raw.githubusercontent.com/tiagohm/MarkdownView/master/4.png'/>
<img width='380' src='https://raw.githubusercontent.com/tiagohm/MarkdownView/master/5.png'/>
<img width='380' src='https://raw.githubusercontent.com/tiagohm/MarkdownView/master/6.png'/>

## LICENSE
```
Copyright 2017 tiagohm

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
