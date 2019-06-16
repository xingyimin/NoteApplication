package com.example.ruler.noteapplication.CodeColoring;

import android.graphics.Color;

import com.yydcdut.rxmarkdown.theme.Theme;


class CodeHighLightTheme implements Theme {

        @Override
        public int getBackgroundColor() {//background color
            return 0xffcccccc;
        }

        @Override
        public int getTypeColor() {//color for type
            return 0xff660066;
        }

        @Override
        public int getKeyWordColor() {//color for keyword
            return 0xff000088;
        }

        @Override
        public int getLiteralColor() {//color for literal
            return 0xff006666;
        }

        @Override
        public int getCommentColor() {//color for comment
            return 0xff880000;
        }

        @Override
        public int getStringColor() {//color for string
            return 0xff008800;
        }

        @Override
        public int getPunctuationColor() {//color for punctuation
            return 0xff666600;
        }

        @Override
        public int getTagColor() {//color for html/xml tag
            return 0xff000088;
        }

        @Override
        public int getPlainTextColor() {//color for a plain text
            return 0xff000000;
        }

        @Override
        public int getDecimalColor() {//color for a markup declaration such as a DOCTYPE
            return 0xff000000;
        }

        @Override
        public int getAttributeNameColor() {//color for html/xml attribute name
            return 0xff660066;
        }

        @Override
        public int getAttributeValueColor() {//color for html/xml attribute value
            return 0xff008800;
        }

        @Override
        public int getOpnColor() {//color for opn
            return 0xff666600;
        }

        @Override
        public int getCloColor() {//color for clo
            return 0xff666600;
        }

        @Override
        public int getVarColor() {//color for var
            return 0xff660066;
        }

        @Override
        public int getFunColor() {//color for fun
            return Color.RED;
        }

        @Override
        public int getNocodeColor() {
            return 0xff000000;
        }

}
