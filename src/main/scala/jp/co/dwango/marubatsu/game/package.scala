package jp.co.dwango.marubatsu

import jp.co.dwango.marubatsu.board.{CellState, Empty, Maru, Batsu}

package object game {

  def toWinner(cellState: CellState): Winner = cellState match {
    case board.Maru => game.Maru
    case board.Batsu => game.Batsu
    case board.Empty => game.NoWinner
  }

}