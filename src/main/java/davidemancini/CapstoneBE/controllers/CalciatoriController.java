package davidemancini.CapstoneBE.controllers;

import davidemancini.CapstoneBE.entities.Calciatori;
import davidemancini.CapstoneBE.payloads.CalciatoriDTO;
import davidemancini.CapstoneBE.services.CalciatoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calciatori")
public class CalciatoriController {
    @Autowired
    private CalciatoriService calciatoriService;

    @GetMapping
    public Page<Calciatori> findAll(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "cognome") String sortBy){
        return calciatoriService.findAll(pageNumber,pageSize,sortBy);
    }

    @GetMapping("/{id}")
    public Calciatori findById(@PathVariable long id){
        return calciatoriService.findById(id);
    }

    @PostMapping("/nuovo-calciatore")
    @ResponseStatus(HttpStatus.CREATED)
    public Calciatori createNewCalciatore (@RequestBody CalciatoriDTO body){
        return calciatoriService.saveNewCalciatore(body);
    }

    @PutMapping("/aggiorna/{id}")
    public Calciatori update (@PathVariable long id, @RequestBody CalciatoriDTO body){
        return calciatoriService.updateCalciatore(id,body);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCalciatore(long id){
        calciatoriService.deleteCalciatore(id);
    }

    @GetMapping("/ruolo/difensori")
    public Page<Calciatori> findDifensori(@RequestParam(defaultValue = "D") String ruolo,@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "valore") String sortBy){
        return calciatoriService.findByRuolo(ruolo,pageNumber,pageSize,sortBy);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<Calciatori>> multiSearch(@RequestParam(required = false) String ruolo,
                                                  @RequestParam(required = false)String cognome,
                                                  @RequestParam(required = false)String squadra,
                                                  @RequestParam(required = false)Long valore,
                                                  @RequestParam(defaultValue = "0") int pageNumber,
                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                  @RequestParam(defaultValue = "valore") String sortBy,
                                                  @RequestParam(defaultValue = "desc") String direction){
        Page<Calciatori> calciatori= calciatoriService.multiSearch(ruolo,cognome,squadra,valore,pageNumber,pageSize,sortBy,direction);
        return ResponseEntity.ok(calciatori);
    }
}
