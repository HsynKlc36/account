package com.hsyntech.account.service;

import com.hsyntech.account.dto.CustomerDto;
import com.hsyntech.account.dto.converter.CustomerDtoConverter;
import com.hsyntech.account.exception.CustomerNotFoundException;
import com.hsyntech.account.model.Customer;
import com.hsyntech.account.repository.CustomerRepository;

import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;


import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {
    private CustomerService service;
    private  CustomerRepository customerRepository;
    private  CustomerDtoConverter customerDtoConverter;
    @BeforeEach
    public  void  setUp(){
        customerRepository= mock(CustomerRepository.class);
        customerDtoConverter=mock(CustomerDtoConverter.class);
        service=new CustomerService(customerRepository,customerDtoConverter);
    }
    //findByCustomerId metodu için iki senaryo test edilmiştir birincisi customer nesnesinin bulunması durumu 2.si ise bulanamaması durumu
    @Test
    public void  testFindByCustomerId_whenCustomerIdExists_shouldReturnCustomer(){
        Customer customer=new Customer("id","name","surname", Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));//optional.of() metodunda değer null gelirse hata fırlatması için of() metoduna başvurulur.null olduğunu düşündüğünüz bir nesnede ise Optional.ofNullable(...) kullanabilirsiniz!

        Customer result=service.findCustomerById("id");
        assertEquals(result,customer);
    }
    @Test
    public void  testFindByCustomerId_whenCustomerIdDoesNotExists_shouldThrowCustomerNotFoundException(){

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());//senaryo gereği null gelebilir senaryosunu incelediğimiz için buranın hata fırlatmaması gerekir ki aşağıda hatamızı oluşturalım.Çünkü zaten null gelebilir bir senaryo inceliyoruz!

       assertThrows(CustomerNotFoundException.class,()->service.findCustomerById("id"));
    }
    //burada ise diğer bir public method olan getCustomerById() metodundaki durumlar ele alınır!!önce senaryonun başarılı olma durumunu elel alalım!
    @Test
    public void  testGetCustomerById_whenCustomerIdExists_shouldReturnCustomerDto(){
        Customer customer=new Customer("id","name","surname", Set.of());
        CustomerDto customerDto=new CustomerDto("id","name","surname",Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));//optional.of() metodunda değer null gelirse hata fırlatması için of() metoduna başvurulur.null olduğunu düşündüğünüz bir nesnede ise Optional.ofNullable(...) kullanabilirsiniz!fakat burda hata fırlatırsa testimiz başarısız olur sebebi ise biz bu senaryoda zaten bir customer nesnesinin varlığı üzerinden gideriz var olmaması bizim senaryomuzu bozacağı için hata fırlatmalıyızki testimizin başarısız olduğunu anlayalım
        Mockito.when(customerDtoConverter.convertToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto result=service.getCustomerById("id");
        assertEquals(result,customerDto);
    }
    @Test
    public void  testGetCustomerById_whenCustomerIdDoesNotExists_sholdReturnThrowCustomerNotFoundException(){
        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class,
                ()->service.getCustomerById("id"));
        Mockito.verifyNoInteractions(customerDtoConverter);// bu ise bir hata fırlatılacağı için coverter mock'u ile herhangi bir etkileşime geçilmediğini gösterir!çünkü öncesinde hata fırlatıldığı için servisteki bu converter metodun da işlem yapılmayacağını doğrulamak için kullanırız çünkü null gelince hata olacağından bu metot boşa düşer ve kullanılmaz!!servisleri incele tekrardan!!Özetle converter mock'unun hiçbir metodu çağırılmadı demektir!
    }
}
//ayrıca public ve geriye dönüş yapan  metotlar unit test ile test edilmelidir diğer taraftan static metotlarda test edilebilir fakat PowerMock kütüphanesi kullanılarak test edilmelidir!!Mockito.when() metodu aslında servisimizdeki yapılan işlemin davranışını belirtmek için kullanılır yani bu davranısı uygula ve neticesinde böyle bişey dön!testlerde public olmalıdır ve geri dönüş değeri olmamalıdır!