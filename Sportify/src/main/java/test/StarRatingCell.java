package test;

import entities.Post;
import entities.StarRating;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;

class StarRatingCell extends TableCell<Post, Integer> {
    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            StarRating starRating = new StarRating(item);
            setText(starRating.display());
        } else {
            setText(null);
        }
    }

    public ListCell<Post> call(ListView<Post> listView) {
        return new ListCell<Post>() {
            @Override
            protected void updateItem(Post item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    // setText(new StarRating(item.getRate()).display()+"             "  +item.getType_equip()+"              "+item.getdate_equip()+"       "+item.getReclamation());
                }
            }
        };
    }
}; //search();
