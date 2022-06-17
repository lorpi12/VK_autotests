package api.cucumber_style;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableTypeRegistry;
import io.cucumber.datatable.DataTableTypeRegistryTableConverter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanit.at.api.models.RequestModel;
import ru.lanit.at.steps.api.ApiSteps;

import java.io.IOException;
import java.util.*;

public class AddEmployeeHr7Test {

    private final ApiSteps apiSteps = new ApiSteps();

    @BeforeMethod
    public void authorization() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config/configuration.properties"));
        RequestModel requestModel = new RequestModel("POST", "authAdmin.json", null, System.getProperty("baseUrl")+"/login");
        apiSteps.createRequest(requestModel);
        DataTable dataTable = DataTable.create(
                Arrays.asList(
                        Arrays.asList("Content-Type", "application/json"),
                        Arrays.asList("Accept", "application/json")
                ), new DataTableTypeRegistryTableConverter(new DataTableTypeRegistry(Locale.ENGLISH)));
        apiSteps.addHeaders(dataTable);
        apiSteps.send();
        apiSteps.expectStatusCode(200);
        Map<String, String> map = new HashMap<>();
        map.put("token", "$.token");
        apiSteps.extractVariables(map);
    }

    @Test
    public void test() {
        RequestModel requestModel = new RequestModel("GET", null, null, System.getProperty("baseUrl")+"/accounts/");
        apiSteps.createRequest(requestModel);
        DataTable dataTable = DataTable.create(Arrays.asList(
                Arrays.asList("Content-Type", "application/json"),
                Arrays.asList("Accept", "application/json")
        ), new DataTableTypeRegistryTableConverter(new DataTableTypeRegistry(Locale.ENGLISH)));
        apiSteps.addHeaders(dataTable);
        apiSteps.addAuthorisation();
        apiSteps.send();
        apiSteps.expectStatusCode(200);
    }

}
