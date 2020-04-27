package jp.co.dwango.marubatsu.board

sealed abstract class CellState
case object Empty extends CellState
case object Maru extends CellState
case object Batsu extends CellState
