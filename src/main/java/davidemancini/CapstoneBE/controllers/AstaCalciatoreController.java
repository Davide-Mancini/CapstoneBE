package davidemancini.CapstoneBE.controllers;

import davidemancini.CapstoneBE.entities.AstaCalciatore;
import davidemancini.CapstoneBE.payloads.AstaCalciatoreDTO;
import davidemancini.CapstoneBE.services.AstaCalciatoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/astacalciatore")
public class AstaCalciatoreController {
    @Autowired
    private AstaCalciatoreService astaCalciatoreService;


    @PostMapping("/nuova-asta")
    public AstaCalciatore newAsta (@RequestBody AstaCalciatoreDTO body){
        return astaCalciatoreService.save(body);

    }

    @GetMapping("/{id}")
    public AstaCalciatore getById (@PathVariable UUID id){
        return astaCalciatoreService.findById(id);
    }
}
