# {{app_name}}

---

# Emphasis

To create **bold** or *italic*, wrap with asterisks * or underscores _.

To avoid creating __bold__ or _italic_, place a backslash in front \\* or \\_.

Any <red>word</red> wrapped with two tildes (like ~~this~~) will appear crossed out.

# Paragraphs

A paragraph is consecutive lines of text with one or more blank lines between them. { .text-right .green .text-small }

For a line break, add either a backslash \ or two blank spaces at the end of the line. { .text-left .blue .text-large }

This is the first paragraph. { .text-center .orange }

This is the second paragraph. { .text-justify .purple }

This is a\
line break.

# Headings

Starting a line with a hash # and a space makes a header.

The more #, the smaller the header.

# Heading 1
## Heading 2
### Heading 3
#### Heading 4
##### Heading 5
###### Heading 6

# Links

Links can be either inline with the text, or placed at the bottom of the text as references.

Link text is enclosed by square brackets [], and for inline links, the link URL is enclosed by parens ().

[Gooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooogle](https://www.google.com)

# Images
Images are almost identical to links, but an image starts with an exclamation point !.

![](https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png@100%|100px)
![](https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png@100px|auto)

# Code
To create inline code, wrap with backticks `.

To create a code block, either indent each line by 4 spaces, or place 3 backticks ``` on a line above and below the code block.

Inline `code`

    indent 4 spaces

```
Or use 3 backticks
```

```javascript
var s = "JavaScript syntax highlighting";
alert(s);
```
 
```python
s = "Python syntax highlighting"
print s
```
 
```
No language indicated, so no syntax highlighting. 
But let's throw in a <b>tag</b>.
```

# Blockquotes
To create a blockquote, start a line with greater than > followed by an optional space.

Blockquotes can be nested, and can also contain other formatting.

> First line
> Another line
>
> > Nested line
>
> Last line

# Lists
Unordered lists can use either asterisks *, plus +, or hyphens - as list markers.
Ordered lists use numbers followed by period . or right paren ).

* Apples
* Oranges
* Pears

1. First
2. Second
3. Third

# Nested Lists
To nest one list within another, indent each item in the sublist by four spaces. You can also nest other elements like paragraphs, blockquotes or code blocks.

* Item
    1. First Subitem
    2. Second Subitem
* Item
    - Subitem
    - Subitem
* Item

# Tables

| day         | time  |   spent |
|:------------|:-----:|--------:|
| nov. 2. tue | 10:00 |  4h 40m |
| nov. 3. thu | 11:00 |      4h |
| nov. 7. mon | 10:20 |  4h 20m |

# TaskList

- [x] this is a complete item
- [ ] this is an incomplete item

# AutoLink

http://www.github.com/ { #id-1 }

# Abbreviation

*[HTML]: Hyper Text Markup Language
*[W3C]:  World Wide Web Consortium
The HTML specification
is maintained by the W3C.

# Mark

This is a ==Marked Text==.

# Subscript & Superscript

200 ml de H~2~O têm 6,69 * 10^24^ moléculas

# Keystrokes

@ctrl+alt+del@

# MathJax
When $a \ne 0$, there are two solutions to $ax^2 + bx + c = 0$ and they are
$$x = {-b \pm \sqrt{b^2-4ac} \over 2a}.$$

# Footnote

Footnote 1 link[^first].

Footnote 2 link[^second].

Duplicated footnote reference[^second].

[^first]: Footnote **can have markup**

    and multiple paragraphs.

[^second]: Footnote text.

# Emoji

:japanese_goblin::japanese_ogre::turkey::owl::jack_o_lantern::fish::rice_ball::space_invader::flag_br::cupid::telescope:

# Video

Wow
@[youtube](g2ag8t7AvX8)

# Twitter

### Embed a Single Tweet
#[tweet](845101936550469634)
#[tweet-hide-cards](845101936550469634)

### Follow Button
Seguir: #[follow](tiag0hm)

# Label

--DEFAULT-- ---SUCCESS--- ----WARNING---- -----DANGER-----

# Bean

{{diasDaSemana.name.toLowerCase}}{ .red }