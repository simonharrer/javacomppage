# java.by-comparison.com

Website of the book *Java by Comparison* by Simon Harrer, Jörg Lenhard, and Linus Dietz.

The whole site is generated from this repository. Nothing in the output is edited by hand.

## Structure

| Path | Content |
|---|---|
| `src/main/pages/` | Hand-written pages (home, story, teaching, research, translations, kata, game, legal). Each file starts with a front matter comment (`title`, `description`, `canonical`, optional `og_*` and `robots`), then an optional `<!-- head -->` block, then the body. |
| `src/main/java/views/layout.rocker.html` | The one layout every page uses: head and meta tags, navigation, buy bar, footer. |
| `src/main/java/views/navigation.rocker.html` | The main navigation. |
| `src/main/java/views/` (other templates) | The comparison pages under `/site/`. |
| `src/main/java/com/bycomparison/` | The generator, and the comparisons themselves (`Comparisons*.java`, code under `javaconcurrency/`, `modernjava/`, ...). |
| `static/` | Copied as-is: CSS, JS, images, fonts, talk slides, `CNAME`, `robots.txt`, `llms.txt`. |

The generator also writes `sitemap.xml` from all pages, so new pages appear there automatically.

## Build

```sh
./build                      # or: ./mvnw clean package exec:java
open target/site/index.html
```

Add `-Dexec.args="target/site --drafts"` to also render the unpublished comparisons into `target/site-drafts`.

## Deployment

`.github/workflows/pages.yml` builds the site on every push to `master` and deploys `target/site` to GitHub Pages
(custom domain java.by-comparison.com). Pull requests are built but not deployed. Dependabot keeps the Maven
dependencies and the GitHub Actions up to date.
