package com.bycomparison;

import java.nio.file.Path;

/**
 * @param output      directory the website is built into
 * @param staticFiles directory copied as-is into the output (CSS, JS, images, slides, ...)
 * @param pages       directory with the hand-written pages
 * @param published   only published comparisons (the public site) or all of them (drafts)
 * @param preview     generate preview card images with carbon-now (needs carbon-now-cli)
 */
record GeneratorConfiguration(Path output, Path staticFiles, Path pages, boolean published, boolean preview) {

}
