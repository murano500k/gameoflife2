package com.stc.gameoflife2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stc.gameoflife2.engine.ConwayGameEngine
import com.stc.gameoflife2.engine.GameEngine
import com.stc.gameoflife2.model.GameConfig
import com.stc.gameoflife2.model.GameRules
import com.stc.gameoflife2.model.GameState
import com.stc.gameoflife2.model.Grid
import com.stc.gameoflife2.model.Pattern
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel managing game state and user interactions.
 */
class GameViewModel(
    private val engine: GameEngine = ConwayGameEngine()
) : ViewModel() {

    private val _state = MutableStateFlow(GameState.initial())
    val state: StateFlow<GameState> = _state.asStateFlow()

    private var gameLoopJob: Job? = null

    // ========== Cell Interactions ==========

    fun toggleCell(row: Int, col: Int) {
        _state.update { currentState ->
            currentState.copy(
                grid = currentState.grid.toggleCell(row, col)
            )
        }
    }

    fun setCell(row: Int, col: Int, alive: Boolean) {
        _state.update { currentState ->
            currentState.copy(
                grid = currentState.grid.setCell(row, col, alive)
            )
        }
    }

    // ========== Simulation Controls ==========

    fun play() {
        if (_state.value.isRunning) return

        _state.update { it.copy(isRunning = true) }
        startGameLoop()
    }

    fun pause() {
        _state.update { it.copy(isRunning = false) }
        gameLoopJob?.cancel()
        gameLoopJob = null
    }

    fun togglePlayPause() {
        if (_state.value.isRunning) pause() else play()
    }

    fun step() {
        if (_state.value.isRunning) return
        advanceGeneration()
    }

    fun clear() {
        pause()
        _state.update { currentState ->
            currentState.copy(
                grid = currentState.grid.clear(),
                generation = 0
            )
        }
    }

    fun randomize(density: Float = 0.3f) {
        pause()
        _state.update { currentState ->
            currentState.copy(
                grid = Grid.randomized(
                    currentState.config.rows,
                    currentState.config.cols,
                    density
                ),
                generation = 0
            )
        }
    }

    // ========== Configuration ==========

    fun setSpeed(speedMs: Long) {
        val clampedSpeed = speedMs.coerceIn(GameConfig.MIN_SPEED_MS, GameConfig.MAX_SPEED_MS)
        _state.update { currentState ->
            currentState.copy(
                config = currentState.config.copy(speedMs = clampedSpeed)
            )
        }

        if (_state.value.isRunning) {
            gameLoopJob?.cancel()
            startGameLoop()
        }
    }

    fun setGridSize(rows: Int, cols: Int) {
        val clampedRows = rows.coerceIn(GameConfig.MIN_SIZE, GameConfig.MAX_SIZE)
        val clampedCols = cols.coerceIn(GameConfig.MIN_SIZE, GameConfig.MAX_SIZE)

        pause()
        _state.update { currentState ->
            currentState.copy(
                grid = currentState.grid.resize(clampedRows, clampedCols),
                config = currentState.config.copy(rows = clampedRows, cols = clampedCols),
                generation = 0
            )
        }
    }

    fun setRules(rules: GameRules) {
        _state.update { currentState ->
            currentState.copy(
                config = currentState.config.copy(rules = rules)
            )
        }
    }

    fun toggleWrapEdges() {
        _state.update { currentState ->
            currentState.copy(
                config = currentState.config.copy(wrapEdges = !currentState.config.wrapEdges)
            )
        }
    }

    // ========== Pattern Placement ==========

    fun selectPattern(pattern: Pattern) {
        _state.update { it.copy(selectedPattern = pattern) }
    }

    fun cancelPatternPlacement() {
        _state.update { it.copy(selectedPattern = null) }
    }

    fun placePattern(row: Int, col: Int) {
        _state.update { currentState ->
            val pattern = currentState.selectedPattern ?: return@update currentState
            currentState.copy(
                grid = currentState.grid.placePattern(pattern, row, col),
                selectedPattern = null
            )
        }
    }

    // ========== Internal ==========

    private fun startGameLoop() {
        gameLoopJob = viewModelScope.launch {
            while (_state.value.isRunning) {
                advanceGeneration()
                delay(_state.value.config.speedMs)
            }
        }
    }

    private fun advanceGeneration() {
        _state.update { currentState ->
            val newGrid = engine.nextGeneration(
                currentState.grid,
                currentState.config.rules,
                currentState.config.wrapEdges
            )
            currentState.copy(
                grid = newGrid,
                generation = currentState.generation + 1
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        gameLoopJob?.cancel()
    }
}