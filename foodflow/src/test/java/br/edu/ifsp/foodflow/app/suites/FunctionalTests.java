package br.edu.ifsp.foodflow.app.suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages({"br.edu.ifsp.foodflow.app.domain", "br.edu.ifsp.foodflow.app.application.useCases"})
@SuiteDisplayName("Todos os testes baseados na técnica funcional")
@IncludeTags({"Functional"})
public class FunctionalTests {
}
