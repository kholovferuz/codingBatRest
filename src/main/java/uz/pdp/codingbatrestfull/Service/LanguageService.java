package uz.pdp.codingbatrestfull.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.Entity.Language;
import uz.pdp.codingbatrestfull.Repository.LanguageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {
    @Autowired
    LanguageRepository languageRepository;

    // READ all languages
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    // READ language by id
    public Language getOneLanguage(Integer id) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        return optionalLanguage.orElse(null);
    }

    // CREATE new language
    public Result addLanguage(Language language) {
        boolean exists = languageRepository.existsByName(language.getName());
        if (exists) {
            return new Result("This language already exists", false);
        }

        languageRepository.save(language);
        return new Result("Language added", true);
    }

    // UPDATE language by id
    public Result updateLanguage(Integer id, Language language) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (optionalLanguage.isPresent()) {
            boolean exists = languageRepository.existsByName(language.getName());
            if (exists) {
                return new Result("This language already exists", false);
            }
            Language editedLanguage = optionalLanguage.get();
            editedLanguage.setName(language.getName());

            languageRepository.save(editedLanguage);
            return new Result("Language updated", true);
        }
        return new Result("Language with this id is not found", false);
    }

    // DELETE language by id
    public Result deleteLanguageById(Integer id) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (optionalLanguage.isPresent()) {
            languageRepository.deleteById(id);
            return new Result("Language deleted", true);
        }
        return new Result("Language with this id is not found", false);
    }
}
