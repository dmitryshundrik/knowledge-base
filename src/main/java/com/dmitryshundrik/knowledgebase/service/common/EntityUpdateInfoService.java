package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.EntityUpdateInfo;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EntityUpdateInfoService {

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CompositionService compositionService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CocktailService cocktailService;

    public List<EntityUpdateInfo> getAll() {
        List<EntityUpdateInfo> allUpdateInfos = new ArrayList<>();

        allUpdateInfos.addAll(getMusicianUpdates());
        allUpdateInfos.addAll(getAlbumUpdates());
        allUpdateInfos.addAll(getCompositionUpdates());
        allUpdateInfos.addAll(getRecipeUpdates());
        allUpdateInfos.addAll(getCocktailUpdates());

        return allUpdateInfos.stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .limit(20)
                .collect(Collectors.toList());
    }

    private List<EntityUpdateInfo> getMusicianUpdates() {
        List<EntityUpdateInfo> musicianUpdateInfo = new ArrayList<>();
        List<Musician> latestUpdate = musicianService.getLatestUpdate();
        for (Musician musician : latestUpdate) {
            musicianUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(musician.getCreated())
                    .archiveSection("музыка:")
                    .description(musician.getNickName())
                    .link("music/musician/" + musician.getSlug())
                    .build());
        }
        return musicianUpdateInfo;
    }

    private List<EntityUpdateInfo> getAlbumUpdates() {
        List<EntityUpdateInfo> albumUpdateInfo = new ArrayList<>();
        List<Album> latestUpdate = albumService.getLatestUpdate();
        for (Album album : latestUpdate) {
            albumUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(album.getCreated())
                    .archiveSection("музыка:")
                    .description("альбом " + album.getTitle() + " архив " + album.getMusician().getNickName())
                    .link("music/musician/" + album.getMusician().getSlug())
                    .build());
        }
        return albumUpdateInfo;
    }

    private List<EntityUpdateInfo> getCompositionUpdates() {
        List<EntityUpdateInfo> compositionUpdateInfo = new ArrayList<>();
        List<Composition> latestUpdate = compositionService.getLatestUpdate();
        for (Composition composition : latestUpdate) {
            compositionUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(composition.getCreated())
                    .archiveSection("музыка:")
                    .description("композиция " + composition.getTitle() + " архив " + composition.getMusician().getNickName())
                    .link("music/musician/" + composition.getMusician().getSlug())
                    .build());
        }
        return compositionUpdateInfo;
    }

    private List<EntityUpdateInfo> getRecipeUpdates() {
        List<EntityUpdateInfo> recipeUpdateInfo = new ArrayList<>();
        List<Recipe> latestUpdate = recipeService.getLatestUpdate();
        for (Recipe recipe : latestUpdate) {
            recipeUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(recipe.getCreated())
                    .archiveSection("гастрономия:")
                    .description("рецепт " + recipe.getTitle())
                    .link("gastronomy/recipe/" + recipe.getSlug())
                    .build());
        }
        return recipeUpdateInfo;
    }

    private List<EntityUpdateInfo> getCocktailUpdates() {
        List<EntityUpdateInfo> cocktailUpdateInfo = new ArrayList<>();
        List<Cocktail> latestUpdate = cocktailService.getLatestUpdate();
        for (Cocktail cocktail : latestUpdate) {
            cocktailUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(cocktail.getCreated())
                    .archiveSection("гастрономия:")
                    .description("коктейль " + cocktail.getTitle())
                    .link("gastronomy/cocktail/" + cocktail.getSlug())
                    .build());
        }
        return cocktailUpdateInfo;
    }

}
