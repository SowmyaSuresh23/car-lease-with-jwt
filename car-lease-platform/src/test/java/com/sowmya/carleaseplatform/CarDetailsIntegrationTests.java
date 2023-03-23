package com.sowmya.carleaseplatform;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.google.gson.Gson;
import com.sowmya.carleaseplatform.constants.CarLeaseServiceConstants;
import com.sowmya.carleaseplatform.dto.CarDetailsRequest;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class CarDetailsIntegrationTests {

  @Autowired
  private MockMvc mvc;

  private Gson gson = new Gson();

  @Test
  @Order(1)
  public void addCarTest() throws Exception {
    CarDetailsRequest newCar = CarDetailsRequest.builder().carNumber("2-XXX-90").make("Toyota")
        .model("Camry").version("2").noOfDoors(4).co2Emission(125D).grossPrice(40000D)
        .interestRate(4F).netPrice(38000D).mileage(19F).userName("Sowmya").build();


    mvc.perform(post("/carDetails/saveCarDetails").contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(newCar))).andExpect(status().isOk())
        .andExpect(jsonPath("responseCode", is(CarLeaseServiceConstants.SUCCESS_CODE)));
  }

  @Test
  @Order(2)
  public void updateCarTest() throws Exception {
    CarDetailsRequest newCar = CarDetailsRequest.builder().carNumber("2-XXX-90").make("Toyota")
        .model("Camry-1").version("1").noOfDoors(4).co2Emission(125D).grossPrice(40000D)
        .interestRate(4F).netPrice(38000D).mileage(19F).userName("Sowmya").build();

    mvc.perform(patch("/carDetails/updateCarDetails").contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(newCar))).andExpect(status().isOk())
        .andExpect(jsonPath("responseCode", is(CarLeaseServiceConstants.SUCCESS_CODE)));
  }

  @Test
  @Order(3)
  public void getCarDetailsTest() throws Exception {
    mvc.perform(get("/carDetails/getCarDetails").contentType(MediaType.APPLICATION_JSON)
        .param("carNumber", "2-XXX-90")).andExpect(status().isOk())
        .andExpect(jsonPath("responseCode", is(CarLeaseServiceConstants.SUCCESS_CODE)));
  }

  @Test
  @Order(4)
  public void deleteCarTest() throws Exception {
    mvc.perform(delete("/carDetails/deleteCar").contentType(MediaType.APPLICATION_JSON)
        .param("carNumber", "2-XXX-90")).andExpect(status().isOk())
        .andExpect(jsonPath("responseCode", is(CarLeaseServiceConstants.SUCCESS_CODE)));
  }
}
