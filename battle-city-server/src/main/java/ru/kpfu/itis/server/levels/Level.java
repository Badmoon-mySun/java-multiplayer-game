package ru.kpfu.itis.server.levels;

import javafx.scene.layout.Pane;
import ru.kpfu.itis.entities.models.blocks.BlockModel;

import java.util.List;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public abstract class Level extends Pane {
    public abstract List<BlockModel> getBlocks();
}
