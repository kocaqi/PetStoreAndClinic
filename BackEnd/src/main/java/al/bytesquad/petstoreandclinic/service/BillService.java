package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Bill;
import al.bytesquad.petstoreandclinic.entity.Client;
import al.bytesquad.petstoreandclinic.entity.Transaction;
import al.bytesquad.petstoreandclinic.payload.entityDTO.BillDTO;
import al.bytesquad.petstoreandclinic.repository.BillRepository;
import al.bytesquad.petstoreandclinic.repository.ClientRepository;
import al.bytesquad.petstoreandclinic.repository.TransactionRepository;
import al.bytesquad.petstoreandclinic.repository.UserRepository;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public BillService(BillRepository billRepository, ModelMapper modelMapper,
                       ClientRepository clientRepository,
                       UserRepository userRepository,
                       TransactionRepository transactionRepository) {
        this.billRepository = billRepository;
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new PropertyMap<Bill, BillDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public BillDTO generate(long clientId, Principal principal) {
        Bill bill = new Bill();
        Client client = clientRepository.findClientById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client", "id", clientId));
        //bill.setUser(userRepository.findByEmail(principal.getName()));
        bill.setClient(client);
        List<Transaction> transactions = transactionRepository.findAllByClientAndPaid(client, false);
        double total = 0;
        for (Transaction t : transactions) {
            total += t.getTotal();
            t.setPaid(true);
        }
        bill.setAmount(total);

        Bill savedBill = billRepository.save(bill);
        for (Transaction t : transactions) {
            t.setBill(bill);
            transactionRepository.save(t);
        }

        return modelMapper.map(savedBill, BillDTO.class);
    }
}
