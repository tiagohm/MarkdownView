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
 android:layout_width="match_parent"
 android:layout_height="match_parent"/>
```
```java
mMarkdownView = (MarkdownView)findViewById(R.id.mark_view);
mMarkdownView.loadMarkdownFromAsset("markdown1.md", MarkdownView.Styles.GITHUB);
//or
mMarkdownView.loadMarkdown("**MarkdownView**", MarkdownView.Styles.GITHUB);
```

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

<img width='480' src='https://raw.githubusercontent.com/tiagohm/MarkdownView/develop/1.png'/>
<img width='480' src='https://raw.githubusercontent.com/tiagohm/MarkdownView/develop/2.png'/>
<img width='480' src='https://raw.githubusercontent.com/tiagohm/MarkdownView/develop/3.png'/>
