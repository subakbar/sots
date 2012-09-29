class CreateWords < ActiveRecord::Migration
  def change
    create_table :words do |t|
      t.string :subject
      t.string :paragraph
      t.text :note
      t.string :tag
      t.string :prefix
      t.integer :memorized
      t.integer :priority

      t.timestamps
    end
  end
end
