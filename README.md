# Seminar1-Generics-Collections

> Mục tiêu tài liệu tập trung vào các chủ đề: Java IO, Hướng đối tượng, Generic và Collections. Sinh viên xây dựng một ứng dụng Slang Dictionary. Ứng dụng Console/Swing/Java FX hỗ trợ các chức năng chính sau đây.

## Yêu cầu chung

1. Phải sử dụng Java IO, OOP, Collections. Ứng dụng có thể là Console, Swing hoặc Java FX.
2. Phải có menu để chọn chức năng, dù là ứng dụng console, hay GUI (Swing, Java FX).
3. Load dữ liệu từ file và đưa vào cấu trúc dữ liệu đã tổ chức sẵn, nhằm tăng tốc độ tìm kiếm. Đảm bảo có thể tìm kiếm nhanh (dưới 1s) cho trường hợp có đến 100.000 slang words. Cấu trúc dữ liệu cần phải được lưu trữ lại, để các lần chạy chương trình tiếp theo, ko cần tổ chức (index) lại dữ liệu.
4. Cần lưu danh sách slang word sau khi đã add, edit, delete.
5. Sử dụng Git (GitHub/Bitbucket/GitLab) để quản lí source code. Khi nộp bài, cần nộp kèm hình chụp các lần commit. Phải có ít nhất 10 commit, nếu ít hơn sẽ 0d. Các commits phải phân bố đều trong các ngày, tránh tình trạng toàn bộ các commit nằm trong 30 phút cuối deadline.

## Các chức năng

1. Chức năng tìm kiếm theo slang word.
2. Chức năng tìm kiếm theo definition, hiển thị ra tất cả các slang words mà trong defintion có chứa keyword gõ vào.
3. Chức năng hiển thị history, danh sách các slang word đã tìm kiếm.
4. Chức năng add 1 slang words mới. Nếu slang words trùng thì thông báo cho người dùng, confirm có overwrite hay duplicate ra 1 slang word mới.
5. Chức năng edit 1 slang word.
6. Chức năng delete 1 slang word. Confirm trước khi xoá.
7. Chức năng reset danh sách slang words gốc.
8. Chức năng random 1 slang word (On this day slang word).
9. Chức năng đố vui, chương trình hiển thị 1 random slang word, với 4 đáp án cho người dùng chọn.
10. Chức năng đố vui, chương trình hiển thị 1 definition, với 4 slang words đáp án cho người dùng chọn.
