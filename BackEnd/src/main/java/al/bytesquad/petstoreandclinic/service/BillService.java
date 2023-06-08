package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Admin;
import al.bytesquad.petstoreandclinic.entity.Bill;
import al.bytesquad.petstoreandclinic.payload.entityDTO.AdminDTO;
import al.bytesquad.petstoreandclinic.payload.entityDTO.BillDTO;
import al.bytesquad.petstoreandclinic.repository.BillRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BillService(BillRepository billRepository, ModelMapper modelMapper) {
        this.billRepository = billRepository;
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new PropertyMap<Bill, BillDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
    }
}
