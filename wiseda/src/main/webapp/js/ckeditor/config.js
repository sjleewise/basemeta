/*
Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	 config.language = 'ko';
//	 config.skin = 'office2003';
//	 config.toolbar = '';
	 config.removePlugins = 'elementspath';
	 config.resize_enabled = false;
	 config.uiColor = '#FFFFFF';
//	 config.width = '99%';
//	 config.height = '99%';
	 config.toolbar_Full =
		 [
		     { name: 'document',    items : [ 'Source','-', 'NewPage','DocProps'] },
		     { name: 'clipboard',   items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
		     { name: 'links',       items : [ 'Link','Unlink','Anchor' ] },
//		     { name: 'editing',     items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
//		     { name: 'forms',       items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
		     '/',
		     { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
		     { name: 'paragraph',   items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
		     { name: 'insert',      items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak' ] },
		     '/',
		     { name: 'styles',      items : [ 'Styles','Format','Font','FontSize' ] },
		     { name: 'colors',      items : [ 'TextColor','BGColor' ] },
//		     { name: 'tools',       items : [ 'Maximize', 'ShowBlocks','-','About' ] }
		 ];
};
