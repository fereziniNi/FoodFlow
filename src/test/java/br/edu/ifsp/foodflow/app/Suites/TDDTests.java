package br.edu.ifsp.foodflow.app.Suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages({"br.edu.ifsp.foodflow.app.domain"})
@SuiteDisplayName("Todos os teste criados com TDD")
@IncludeTags({"TDD"})
public class TDDTests {
}
