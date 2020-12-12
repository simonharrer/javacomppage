package com.bycomparison;

import java.nio.file.Path;

record GeneratorConfiguration(Path site, boolean published, boolean preview) {

}
